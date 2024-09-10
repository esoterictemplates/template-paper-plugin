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
  id("xyz.jpenilla.run-paper") version "2.3.0" // Adds runServer and runMojangMappedServer tasks for testing
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1" // Generates plugin.yml based on the Gradle config
}

fun replaceInFile(filePath: String, target: String, replacement: String) {
  val file = Paths.get(filePath)
  if (!Files.exists(file)) {
    throw NoSuchFileException(filePath)
  }
  val content = String(Files.readAllBytes(file))
  val updatedContent = content.replace(target, replacement)
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
    val newAuthor: String = project.findProperty("author")?.toString() ?: error("Please provide an author using -Pauthor")
    val newName: String = project.findProperty("name")?.toString() ?: error("Please provide a name using -Pname")
    val newTopLevelDomain: String = project.findProperty("topLevelDomain")?.toString() ?: error("Please provide a top level domain using -PtopLevelDomain")

    val settingsFilePath = projectDir.resolve("settings.gradle.kts").toString()
    val buildFilePath = projectDir.resolve("build.gradle.kts").toString()

    val currentProjectName = rootProject.name
    replaceInFile(settingsFilePath, currentProjectName, newName)

    replaceInFile(buildFilePath, "val mainProjectAuthor = \"$mainProjectAuthor\"", "val mainProjectAuthor = \"$newAuthor\"")

    replaceInFile(buildFilePath, "val topLevelDomain = \"$topLevelDomain\"", "val topLevelDomain = \"$newTopLevelDomain\"")

    val currentGroup = "$topLevelDomain.$mainProjectAuthor.$projectNameString".replace(" ", snakecaseStringSeparator)
    val newGroup = "$newTopLevelDomain.${newAuthor.lowercase().replace(" ", snakecaseStringSeparator)}.${snakecase(newName)}"
    replaceInFile(buildFilePath, currentGroup, newGroup)

    println("Renamed project to '$newName', author to '$newAuthor', and top-level domain to '$newTopLevelDomain'")
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
