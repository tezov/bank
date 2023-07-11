/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.type.primitive

import com.tezov.lib_core_kotlin.type.primitive.string.StringHexTo

object ByteTo {
    var BYTES = 1
    var SIZE = java.lang.Byte.SIZE

    fun Byte.toBoolean() = this == 0x01.toByte()
    fun Byte?.toBoolean() = this?.toBoolean()

    fun UByte.toBoolean() = this == 0x01.toUByte()
    fun UByte?.toBoolean() = this?.toBoolean()

    fun UByte.toStringHex() = StringHexTo.decode(this)
    fun UByte?.toStringHex() = this?.toStringHex()
}