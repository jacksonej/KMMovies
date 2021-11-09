package com.jackz.kmmovies




actual typealias Image = ByteArray

// 2
actual fun ByteArray.toNativeImage(): Image? = byteArrayOf(0x2E, 0x38)