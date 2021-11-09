## Kotlinx serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.jackz.kmmovies.**$$serializer { *; } # <-- change package name to your app's
-keepclassmembers class com.jackz.kmmovies.** { # <-- change package name to your app's
    *** Companion;
}
-keepclasseswithmembers class com.jackz.kmmovies.** { # <-- change package name to your app's

}