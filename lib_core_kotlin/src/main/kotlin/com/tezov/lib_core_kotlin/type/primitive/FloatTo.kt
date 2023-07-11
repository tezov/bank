/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.type.primitive

import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringHex

@OptIn(ExperimentalUnsignedTypes::class)
object FloatTo {
    var BYTES = java.lang.Float.SIZE / ByteTo.SIZE
    var MAX_DIGIT_DECIMAL = 7

    fun Float.toUByteArray() = this.let {
        val intBits = it.toRawBits()
        ubyteArrayOf(
            (intBits shr 24).toUByte(),
            (intBits shr 16).toUByte(),
            (intBits shr 8).toUByte(),
            intBits.toUByte()
        )
    }

    fun Float?.toUByteArray() = this?.toUByteArray()

    fun Float.toStringHex() = this.toUByteArray().toStringHex()
    fun Float?.toStringHex() = this?.toStringHex()
}