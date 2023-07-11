/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.type.primitive

import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringHex
import com.tezov.lib_core_kotlin.type.primitive.string.StringHexTo

@OptIn(ExperimentalUnsignedTypes::class)
object IntTo {
    var BYTES = Integer.SIZE / ByteTo.SIZE

    var MAX_DIGIT_POSITIVE = Int.MAX_VALUE.toString().length
    var MAX_DIGIT_NEGATIVE = Int.MIN_VALUE.toString().length - 1

    fun Int.toUByteArray() = ubyteArrayOf(
        (this shr 24).toUByte(),
        (this shr 16).toUByte(),
        (this shr 8).toUByte(),
        this.toUByte()
    )

    fun Int?.toUByteArray() = this?.toUByteArray()

    fun Int.toStringHex(addPrefix: Boolean = false) =
        if (!addPrefix) this.toUByteArray().toStringHex()
        else StringHexTo.HEX_PREFIX + this.toUByteArray().toStringHex()

    fun Int?.toStringHex(addPrefix: Boolean = false) = this?.toStringHex(addPrefix)

}