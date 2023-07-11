/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.type.primitive.string

import android.util.Base64
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringChar

@OptIn(ExperimentalUnsignedTypes::class)
object StringBase64To {
    fun encode(b: UByteArray) = Base64.encodeToString(b.toByteArray(), Base64.DEFAULT)
    fun decode(s: String) = Base64.decode(s, Base64.DEFAULT).toUByteArray()

    fun String.toUByteArrayBase64() = decode(this)
    fun String?.toUByteArrayBase64() = this?.toUByteArrayBase64()

    fun String?.toStringChar() = this?.toUByteArrayBase64()?.toStringChar()
}