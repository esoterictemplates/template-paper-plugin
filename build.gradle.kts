import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
  java
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.7.1"
  id("xyz.jpenilla.run-paper") version "2.3.0"
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1"
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

val groupStringSeparator = "."
val kebabcaseStringSeparator = "-"
val snakecaseStringSeparator = "_"

fun capitaliseFirstLetter(string: String): String {
  return string.first().uppercase() + string.slice(IntRange(1, string.length - 1))
}

fun snakecase(kebabcaseString: String): String {
  return kebabcaseString.lowercase().replace(kebabcaseStringSeparator, snakecaseStringSeparator)
}

fun pascalcase(kebabcaseString: String): String {
  var pascalCaseString = ""

  val splitString = kebabcaseString.split(kebabcaseStringSeparator)

  for (part in splitString) {
    pascalCaseString += capitaliseFirstLetter(part)
  }

  return pascalCaseString
}

val mainProjectAuthor = "Slqmy"
val topLevelDomain = "net"
val projectAuthors = listOfNotNull(mainProjectAuthor)

group = topLevelDomain + groupStringSeparator + mainProjectAuthor.lowercase() + groupStringSeparator + snakecase(rootProject.name)
version = "1.0.0-SNAPSHOT"
description = "Test plugin for paperweight-userdev"

val javaVersion = 21
val paperApiVersion = "1.21"

java {
  toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

repositories {
    mavenCentral()
}

dependencies {
  paperweight.paperDevBundle(paperApiVersion + "-R0.1-SNAPSHOT")

  implementation("dev.jorel" , "commandapi-bukkit-shade-mojang-mapped" , "9.5.1")
}

tasks {
  compileJava {
    options.release = javaVersion
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }

  shadowJar {
    fun relocatePackage(packageName: String) = relocate(packageName, project.group.toString() + groupStringSeparator + "$packageName")
  }
}

bukkitPluginYaml {
  main = project.group.toString() + groupStringSeparator + pascalcase(rootProject.name)
  load = BukkitPluginYaml.PluginLoadOrder.STARTUP
  authors = projectAuthors
  apiVersion = paperApiVersion
}
