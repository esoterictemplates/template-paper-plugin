import org.gradle.api.JavaVersion
import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.nio.file.NoSuchFileException

plugins {
  java
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.7.2"
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1"
  id("xyz.jpenilla.run-paper") version "2.3.0"

  id("io.github.goooler.shadow") version "8.1.7"
}

val groupStringSeparator = "."
val kebabcaseStringSeparator = "-"
val snakecaseStringSeparator = "_"

fun capitalizeFirstLetter(string: String): String {
  return string.first().uppercase() + string.slice(IntRange(1, string.length - 1))
}

fun kebabcase(normalString: String): String {
  return normalString.lowercase().replace(" ", kebabcaseStringSeparator)
}

fun snakecase(string: String): String {
  return string.lowercase().replace(Regex("$kebabcaseStringSeparator| "), snakecaseStringSeparator)
}

fun pascalcase(string: String): String {
  var pascalCaseString = ""

  val splitString = string.split(Regex("$kebabcaseStringSeparator| "))

  for (part in splitString) {
    pascalCaseString += capitalizeFirstLetter(part)
  }

  return pascalCaseString
}

fun replaceInFile(filePath: String, stringToReplace: String, replacementString: String) {
  val file = Paths.get(filePath)

  if (!Files.exists(file)) {
    throw NoSuchFileException(filePath)
  }

  val content = String(Files.readAllBytes(file))
  val updatedContent = content.replace(stringToReplace, replacementString)

  Files.write(file, updatedContent.toByteArray(), StandardOpenOption.TRUNCATE_EXISTING)
}

description = "Test plugin for paperweight-userdev"

val mainProjectAuthor = "Esoteric Slime"
val projectAuthors = listOfNotNull(mainProjectAuthor)

val topLevelDomain = "net"
 
val projectNameString = rootProject.name

group = topLevelDomain + groupStringSeparator + mainProjectAuthor.lowercase().replace(" ", snakecaseStringSeparator) + groupStringSeparator + snakecase(projectNameString)
version = "0.0.4"

val buildDirectoryString = buildDir.toString()

val projectGroupString = group.toString()
val projectVersionString = version.toString()

val javaVersion = 21
val javaVersionEnumMember = JavaVersion.valueOf("VERSION_$javaVersion")

val paperApiMinecraftVersion = "1.21"
val paperApiVersion = "$paperApiMinecraftVersion-R0.1-SNAPSHOT"

java {
  sourceCompatibility = javaVersionEnumMember
  targetCompatibility = javaVersionEnumMember

  toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

repositories {
  mavenCentral()
}

dependencies {
  paperweight.paperDevBundle(paperApiVersion)

  implementation("dev.jorel" , "commandapi-bukkit-shade-mojang-mapped" , "9.5.1")
  
  implementation("net.lingala.zip4j", "zip4j", "2.11.5")
}

tasks {
  build {
    dependsOn(shadowJar)
  }

  shadowJar {
    archiveFileName = "$projectNameString-$projectVersionString.jar"
  }

  compileJava {
    options.release = javaVersion
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
}

tasks.register("renameProject") {
  doLast {
    val newName = project.findProperty("new-name")?.toString() ?: error("Please provide a new project name using -Pnew-name")
    val newAuthorName = project.findProperty("new-author-name")?.toString() ?: error("Please provide a new author name using -Pnew-author-name")
    val newTopLevelDomain = project.findProperty("new-top-level-domain")?.toString() ?: error("Please provide a new top level domain using -Pnew-top-level-domain")

    val newSnakecaseName = snakecase(newName)
    val newSnakecaseAuthorName = snakecase(newAuthorName)
    val newPascalcaseName = pascalcase(newName)

    val newGroup = "$newTopLevelDomain.$newSnakecaseAuthorName.$newSnakecaseName"

    val settingsFilePath = projectDir.resolve("settings.gradle.kts").toString()
    val buildFilePath = projectDir.resolve("build.gradle.kts").toString()
    val javaSrcPath = projectDir.resolve("src/main/java").toPath().toFile()

    val currentProjectName = rootProject.name

    val currentGroup = project.group.toString();

    replaceGroupInJavaFiles(javaSrcPath, currentGroup, newGroup)

    replaceInFile(settingsFilePath, currentProjectName, kebabcase(newName))
    replaceInFile(buildFilePath, "val mainProjectAuthor = \"$mainProjectAuthor\"", "val mainProjectAuthor = \"$newAuthorName\"")
    replaceInFile(buildFilePath, "val topLevelDomain = \"$topLevelDomain\"", "val topLevelDomain = \"$newTopLevelDomain\"")

    val oldMainFileName = pascalcase(currentProjectName) + ".java"
    val newMainFileName = "$newPascalcaseName.java"
    renameMainJavaFile(javaSrcPath, oldMainFileName, newMainFileName)

    // Rename package directories
    renamePackageDirectories(javaSrcPath, currentGroup, newGroup)

    println("Renamed project to '$newName', author to '$newAuthorName', and top-level domain to '$newTopLevelDomain'")
  }
}

fun replaceGroupInJavaFiles(directory: File, oldGroup: String, newGroup: String) {
  directory.walkTopDown().filter { it.isFile && it.extension == "java" }.forEach { file ->
    replaceInFile(file.path, oldGroup, newGroup)
    println("Replaced group $oldGroup with $newGroup in ${file.name}")
  }
}

fun renameMainJavaFile(directory: File, oldFileName: String, newFileName: String) {
  directory.walkTopDown().filter { it.isFile && it.name == oldFileName }.forEach { file ->
    val newFile = File(file.parentFile, newFileName)
    if (file.renameTo(newFile)) {
      println("Renamed main Java file from $oldFileName to $newFileName")
    } else {
      println("Failed to rename $oldFileName")
    }
  }
}

fun renamePackageDirectories(baseDir: File, oldGroup: String, newGroup: String) {
  val oldPackagePath = oldGroup.replace(".", File.separator)
  val newPackagePath = newGroup.replace(".", File.separator)

  val oldPackageDir = File(baseDir, oldPackagePath)
  val newPackageDir = File(baseDir, newPackagePath)

  if (oldPackageDir.exists()) {
    if (oldPackageDir.renameTo(newPackageDir)) {
      println("Renamed package directory from $oldPackagePath to $newPackagePath")
    } else {
      println("Failed to rename package directory from $oldPackagePath to $newPackagePath")
    }
  } else {
    println("Package directory $oldPackagePath does not exist")
  }
}

bukkitPluginYaml {
  authors = projectAuthors

  main = projectGroupString + groupStringSeparator + pascalcase(projectNameString)
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
