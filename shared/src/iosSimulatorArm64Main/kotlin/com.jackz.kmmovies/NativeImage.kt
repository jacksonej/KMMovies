package com.jackz.kmmovies

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCValues
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.UIKit.UIImage

actual typealias Image = UIImage

@ExperimentalUnsignedTypes
actual fun ByteArray.toNativeImage(): Image? =
    memScoped {
        toCValues()
            .ptr
            .let { NSData.dataWithBytes(it, size.toULong()) }
            .let { UIImage.imageWithData(it) }
    }