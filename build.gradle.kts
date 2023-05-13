buildscript {

    var kotlin_version: String by extra
    kotlin_version = "1.8.20"
    val composeVersion by extra("1.4.0")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.8.20")
    }
}


allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://androidx.dev/snapshots/builds/7888785/artifacts/repository")

    }
}

//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}