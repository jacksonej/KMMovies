buildscript {

    val compose_version by extra("1.0.5")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven(url = "https://androidx.dev/snapshots/builds/7888785/artifacts/repository")

    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.5.31")
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