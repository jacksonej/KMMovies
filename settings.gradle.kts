pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "KMMovies"
include(":androidApp")
include(":shared")
include(":desktop")
include(":compose-web")
