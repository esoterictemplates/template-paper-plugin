import org.gradle.api.JavaVersion
import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

import java.io.File

plugins {
  java
  `java-library`

  `maven-publish`

  id("io.papermc.paperweight.userdev") version "1.7.3"
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
  id("xyz.jpenilla.run-paper") version "2.3.1"
  id("io.github.goooler.shadow") version "8.1.8"
}

val groupStringSeparator = "."
val kebabcaseStringSeparator = "-"
val snakecaseStringSeparator = "_"

val startPath = "src${File.separator}main${File.separator}java"

fun capitalizeFirstLetter(string: String): String {
  return string.first().uppercase() + string.drop(1)
}

fun titlecase(kebabcaseString: String): String {
  return kebabcaseString.replace(kebabcaseStringSeparator, " ").split(" ").joinToString(" ") { string -> capitalizeFirstLetter(string) }
}

fun kebabcase(normalString: String): String {
  return normalString.lowercase().replace(" ", kebabcaseStringSeparator)
}

fun snakecase(string: String): String {
  return string.lowercase().replace(Regex("$kebabcaseStringSeparator| "), snakecaseStringSeparator)
}

fun pascalcase(string: String): String {
  return string.split(Regex("$kebabcaseStringSeparator| "))
    .joinToString("") { capitalizeFirstLetter(it) }
}

fun groupStringToPath(groupString: String): String {
  return groupString.replace(groupStringSeparator, File.separator)
}

fun replaceStringInFile(filePath: String, stringToReplace: String, replacementString: String) {
  val file = File(filePath)

  val content = file.readText()
  val updatedContent = content.replace(stringToReplace, replacementString)

  file.writeText(updatedContent)
}

fun replaceStringInDirectoryFiles(directory: File, stringToReplace: String, replacementString: String) {
  directory.walkTopDown().filter { it.isFile }.forEach { file ->
    replaceStringInFile(file.path, stringToReplace, replacementString)
  }
}

description = "A template repository for easily developing Minecraft Paper plugins."

val mainProjectAuthorName = "Esoteric Organisation"
val snakecaseMainProjectAuthorName = snakecase(mainProjectAuthorName)

val projectAuthors = listOfNotNull(mainProjectAuthorName)

val topLevelDomain = "org"

val projectNameString = rootProject.name
val snakecaseProjectNameString = snakecase(projectNameString)

group = "$topLevelDomain$groupStringSeparator$snakecaseMainProjectAuthorName"
version = "0.0.4"

val buildDirectoryString = layout.buildDirectory.toString()

val projectGroupString = group.toString()
val projectVersionString = version.toString()

val javaVersion = 21
val javaVersionEnumMember = JavaVersion.valueOf("VERSION_$javaVersion")

val paperApiMinecraftVersion = "1.21.1"
val paperApiVersion = "$paperApiMinecraftVersion-R0.1-SNAPSHOT"

java {
  sourceCompatibility = javaVersionEnumMember
  targetCompatibility = javaVersionEnumMember

  toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
}

repositories {
  mavenCentral()
}

dependencies {
  paperweight.paperDevBundle(paperApiVersion)

  implementation("dev.jorel", "commandapi-bukkit-shade-mojang-mapped", "9.5.1")
  implementation("net.lingala.zip4j", "zip4j", "2.11.5")
}

tasks {
  build {
    dependsOn(shadowJar)
  }

  compileJava {
    options.release.set(javaVersion)
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
}

tasks.register("renameProject") {
  doLast {
    val oldNameInput = titlecase(projectNameString)

    val newName = project.findProperty("new-name")?.toString() ?: oldNameInput
    val newAuthorName = project.findProperty("new-author-name")?.toString() ?: mainProjectAuthorName
    val newTopLevelDomain = project.findProperty("new-top-level-domain")?.toString() ?: topLevelDomain

    val newSnakecaseName = snakecase(newName)
    val newSnakecaseAuthorName = snakecase(newAuthorName)

    val newGroupString = "$newTopLevelDomain$groupStringSeparator$newSnakecaseAuthorName$groupStringSeparator$newSnakecaseName"
    val newGroupPath = groupStringToPath(newGroupString)

    val newMainClassName = pascalcase(newName)
    val newMainClassFileName = "$newMainClassName.java"

    val settingsFilePath = projectDir.resolve("settings.gradle.kts").toString()
    val buildFilePath = projectDir.resolve("build.gradle.kts").toString()
    val javaSourcePath = projectDir.resolve(startPath)

    val currentGroupPath = groupStringToPath(projectGroupString)

    val currentMainClassName = pascalcase(projectNameString)

    if (currentMainClassName != newMainClassName) {
      val currentMainClassFileName = "$currentMainClassName.java"

      val currentMainClassFilePath = File(startPath, "$currentGroupPath${File.separator}$currentMainClassFileName")
      val newMainClassFilePath = File(startPath, "$currentGroupPath${File.separator}$newMainClassFileName")

      currentMainClassFilePath.renameTo(newMainClassFilePath)
    }

    replaceStringInDirectoryFiles(javaSourcePath.parentFile, oldNameInput, newName)
    replaceStringInDirectoryFiles(javaSourcePath.parentFile, projectGroupString, newGroupString)
    replaceStringInDirectoryFiles(javaSourcePath.parentFile, currentMainClassName, newMainClassName)

    replaceStringInFile(settingsFilePath, projectNameString, kebabcase(newName))
    replaceStringInFile(buildFilePath, "val mainProjectAuthorName = \"$mainProjectAuthorName\"", "val mainProjectAuthorName = \"$newAuthorName\"")
    replaceStringInFile(buildFilePath, "val topLevelDomain = \"$topLevelDomain\"", "val topLevelDomain = \"$newTopLevelDomain\"")

    if (topLevelDomain != newTopLevelDomain) {
      File("$startPath${File.separator}$topLevelDomain").renameTo(File("$startPath${File.separator}$newTopLevelDomain"))
    }

    if (snakecaseMainProjectAuthorName != newSnakecaseAuthorName) {
      File("$startPath${File.separator}$newTopLevelDomain${File.separator}$snakecaseMainProjectAuthorName").renameTo(File("$startPath${File.separator}$newTopLevelDomain${File.separator}$newSnakecaseAuthorName"))
    }

    if (snakecaseProjectNameString != newSnakecaseName) {
      File("$startPath${File.separator}$newTopLevelDomain${File.separator}$newSnakecaseAuthorName${File.separator}$snakecaseProjectNameString").renameTo(File("$startPath${File.separator}$newGroupPath"))
    }
  }
}

bukkitPluginYaml {
  description = project.description
  authors = projectAuthors

  main = "org.esoteric.minecraft.plugins.template.TemplatePaperPlugin"
  apiVersion = paperApiMinecraftVersion
  load = BukkitPluginYaml.PluginLoadOrder.STARTUP
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      groupId = projectGroupString
      artifactId = projectNameString
      version = projectVersionString
    }
  }
}

tasks.named("publishMavenJavaPublicationToMavenLocal") {
  dependsOn(tasks.named("build"))
}
