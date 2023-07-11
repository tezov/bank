/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.type.primitive

@OptIn(ExperimentalUnsignedTypes::class)
object BooleanTo {

    fun Boolean.toByte() = if (this) 1.toByte() else 0.toByte()
    fun Boolean?.toByte() = this?.toByte()

    fun Boolean.toUByte() = if (this) 1.toUByte() else 0.toUByte()
    fun Boolean?.toUByte() = this?.toUByte()

    fun Boolean.toUByteArray() = ubyteArrayOf(this.toUByte())
    fun Boolean?.toUByteArray() = this?.toUByteArray()
}