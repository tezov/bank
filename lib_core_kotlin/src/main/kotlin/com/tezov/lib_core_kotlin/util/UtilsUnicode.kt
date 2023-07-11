/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.util

import com.tezov.lib_core_kotlin.application.AppRandomNumber.nextInt
import com.tezov.lib_core_kotlin.file.UtilsStream
import com.tezov.lib_core_kotlin.type.primitive.string.StringCharTo.toUByteArrayChar
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalUnsignedTypes::class)
object UtilsUnicode {

    class Latin {
        private val ranges = ArrayList<IntRange>().apply {
            add(0x0020..0x007E) //ASCII
            add(0x00A0..0x00A0) //supplement
            add(0x0100..0x0148) //extended-A1
            add(0x0149..0x017F) //extended-A2
            add(0x0180..0x024F) //extended-B
            add(0x1E02..0x1EF3) //additional
            add(0x0259..0x0259) //IPA extension
            add(0x027C..0x027C) //IPA extension
            add(0x0292..0x0292) //IPA extension
            add(0x02B0..0x02FF) //IPA extension
        }
        private var length = ranges.fold(0) { acc, value -> acc + value.count() }

        private fun codeAt(length: Int): Int {
            //TODO improve with with flat array and direct random access
            var value = length
            var charCode = 0
            for (r in ranges) {
                if (value < r.count()) {
                    charCode = r.first + value
                    break
                }
                value -= r.count()
            }
            return charCode
        }

        private fun randomCode() = codeAt(nextInt(length))

        fun randomChar() = randomCode().toChar()

        fun randomString() = randomChar().toString()

        fun randomUByteArray() = randomString().toUByteArrayChar()

        fun randomString(length: Int): String {
            val builder = StringBuilder()
            for (i in 0 until length) {
                builder.append(randomChar())
            }
            return builder.toString()
        }

        fun randomUByteArray(length: Int): UByteArray {
            var output: ByteArrayOutputStream? = null
            return try {
                output = ByteArrayOutputStream()
                for (i in 0 until length) {
                    output.write(randomUByteArray().asByteArray())
                }
                val array = output.toByteArray().asUByteArray()
                output.close()
                array
            } catch (e: Throwable) {
                UtilsStream.closeSilently(output)
                ubyteArrayOf()
            }
        }

        fun all(): String {
            val builder = StringBuilder()
            for (i in 0 until length) {
                builder.append(codeAt(i).toChar())
            }
            return builder.toString()
        }

    }
}