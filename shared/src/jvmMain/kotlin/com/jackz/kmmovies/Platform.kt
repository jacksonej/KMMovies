package com.jackz.kmmovies

import io.ktor.util.*


actual class Platform actual constructor() {
    actual val platform: String = "JVM "+PlatformUtils.IS_JVM
}