plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

val kebabcaseStringSeparator = "-"

fun kebabcase(normalString: String): String {
  return normalString.lowercase().replace(" ", kebabcaseStringSeparator)
}

val projectName = providers.gradleProperty("projectName").get()

rootProject.name = kebabcase(projectName)
