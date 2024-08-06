import org.gradle.api.JavaVersion
import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
  java
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.7.2"
  id("xyz.jpenilla.run-paper") version "2.3.0" // Adds runServer and runMojangMappedServer tasks for testing
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1" // Generates plugin.yml based on the Gradle config
}

description = "Test plugin for paperweight-userdev"

val mainProjectAuthor = "Slqmy"
val projectAuthors = listOfNotNull(mainProjectAuthor)

val topLevelDomain = "net"

val projectNameString = rootProject.name

group = topLevelDomain + groupStringSeparator + mainProjectAuthor.lowercase() + groupStringSeparator + snakecase(projectNameString)
version = "0.0.4"

val buildDirectoryString = buildDir.toString()

val projectGroupString = group.toString()
val projectVersionString = version.toString()

val javaVersion = 21
val javaVersionEnumMember = JavaVersion.valueOf("VERSION_" + javaVersion)

val paperApiMinecraftVersion = "1.21"
val paperApiVersion = paperApiMinecraftVersion + "-" + "R0.1-SNAPSHOT"

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
    archiveFileName = projectNameString + "-" + projectVersionString + "." + "jar"
  }

  compileJava {
    options.release = javaVersion
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()
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
