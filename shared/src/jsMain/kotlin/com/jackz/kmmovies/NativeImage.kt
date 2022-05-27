package com.jackz.kmmovies




actual typealias Image = ByteArray

// 2
actual fun ByteArray.toNativeImage(): Image? = null
