import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.7.2"
  id("xyz.jpenilla.run-paper") version "2.3.0" // Adds runServer and runMojangMappedServer tasks for testing
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1" // Generates plugin.yml based on the Gradle config
  // Shades and relocates dependencies into our plugin jar. See https://imperceptiblethoughts.com/shadow/introduction/
  id("io.github.goooler.shadow") version "8.1.7"
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
  // Configure the java toolchain. This allows gradle to auto-provision JDK 21 on systems that only have JDK 11 installed for example.
  toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

// 1)
// For >=1.20.5 when you don't care about supporting spigot
// paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

// 2)
// For 1.20.4 or below, or when you care about supporting Spigot on >=1.20.5
// Configure reobfJar to run when invoking the build task
/*
tasks.assemble {
  dependsOn(tasks.reobfJar)
}
 */

repositories {
    mavenCentral()
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
  paperweight.paperDevBundle(paperApiVersion + "-R0.1-SNAPSHOT")

  compileOnly("dev.jorel", "commandapi-bukkit-core", "9.5.0")
  // paperweight.foliaDevBundle("1.21-R0.1-SNAPSHOT")
  // paperweight.devBundle("com.example.paperfork", "1.21-R0.1-SNAPSHOT")
}

tasks {
  compileJava {
    // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
    // See https://openjdk.java.net/jeps/247 for more information.
    options.release = javaVersion
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
  }

  // Only relevant when going with option 2 above
  /*
  reobfJar {
    // This is an example of how you might change the output location for reobfJar. It's recommended not to do this
    // for a variety of reasons, however it's asked frequently enough that an example of how to do it is included here.
    outputJar = layout.buildDirectory.file("libs/PaperweightTestPlugin-${project.version}.jar")
  }
   */

  shadowJar {
    // helper function to relocate a package into our package
    fun reloc(pkg: String) = relocate(pkg, "io.papermc.paperweight.testplugin.dependency.$pkg")

    // relocate cloud and it's transitive dependencies
    reloc("org.incendo.cloud")
    reloc("io.leangen.geantyref")
  }
}

// Configure plugin.yml generation
// - name, version, and description are inherited from the Gradle project.
bukkitPluginYaml {
  main = project.group.toString() + groupStringSeparator + pascalcase(rootProject.name)
  load = BukkitPluginYaml.PluginLoadOrder.STARTUP
  authors = projectAuthors
  apiVersion = paperApiVersion
}
