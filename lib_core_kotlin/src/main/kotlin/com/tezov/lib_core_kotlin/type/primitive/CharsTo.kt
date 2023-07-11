/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.type.primitive

@OptIn(ExperimentalUnsignedTypes::class)
object CharsTo {

        fun CharArray.toUByteArray() = this.let {
        val b = UByteArray(it.size * 2)
        for (i in b.indices step 2) {
            val code = it[i / 2].code
            b[i] = (code shr 8).toUByte()
            b[i + 1] = code.toUByte()
        }
        b
    }

    fun CharArray?.toUByteArray() = this?.toUByteArray()


}