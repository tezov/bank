/*
 * Copyright (c) 2023.
 * Created by Tezov on 10/07/2023 20:15
 * Last modified 10/07/2023 20:13
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

/*
 *  ********************************************************************************
 *  Created by Tezov under MIT LICENCE.
 *  For any request, please send me an email to tezov.app@gmail.com.
 *  I'll be glad to answer you if your request is sane :)
 *  Last update 2023
 *  ********************************************************************************
 */

package com.tezov.lib_core_kotlin.buffer

import com.google.common.truth.Truth
import com.tezov.lib_core_kotlin.type.collection.ListEntry
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringChar
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringHex
import com.tezov.lib_core_kotlin.util.UtilsNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import kotlin.reflect.KClass

//TODO test write and read back correct value (same bytebuffer)

class ByteBufferBuilderTest {

    companion object {
        const val LOOP = 50
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }


    @Test
    fun bytebufferbuilder_is_equal_to_bytebuffer() {
        //fix data
        val intData = 1
        val floatData = 2.0f
        val doubleData = 3.0
        val longData = 4L
        val byteData = 0x05.toUByte()
        val stringData = "hello world"
        val charsData = stringData.toCharArray()
        val bytesData = UByteArray(10) { index -> 0xAAU }
        val copyBytesData = UByteArray(10) { index -> 0xAAU }
        val copyByteData = 0x06.toUByte()

        // build ByteBuffer
        val length =
            ByteBuffer.CHARS_SIZE(charsData) + ByteBuffer.STRING_SIZE(stringData) + ByteBuffer.BYTES_SIZE(
                bytesData
            ) + ByteBuffer.BYTE_SIZE() + ByteBuffer.INT_SIZE() + ByteBuffer.LONG_SIZE() + ByteBuffer.DOUBLE_SIZE() + ByteBuffer.FLOAT_SIZE() + ByteBuffer.BOOLEAN_SIZE(
                2
            ) + ByteBuffer.FLAG_SIZE(2) + copyBytesData.size + 1
        val bytebuffer = ByteBuffer.obtain(length).apply {
            chars = charsData
            string = stringData
            bytes = bytesData
            byte = byteData
            int = intData
            long = longData
            double = doubleData
            float = floatData
            boolean = true
            boolean = false
            nullFlag = true
            nullFlag = false
            writeToBuffer(copyBytesData)
            writeToBuffer(copyByteData)
        }.uByteArray

        // build ByteBufferBuilder
        val bytebufferbuilder = ByteBufferBuilder.obtain().apply {
            chars = charsData
            string = stringData
            bytes = bytesData
            byte = byteData
            int = intData
            long = longData
            double = doubleData
            float = floatData
            boolean = true
            boolean = false
            nullFlag = true
            nullFlag = false
            writeToBuffer(copyBytesData)
            writeToBuffer(copyByteData)
        }.build().uByteArray

        // verify
        Truth.assertThat(bytebuffer).containsExactlyElementsIn(bytebufferbuilder).inOrder()
    }

    @Test
    fun random_bytebufferbuilder_is_equal_to_bytebuffer() {
        for (i in 0..LOOP) {
            //random data
            val datas: ListEntry<KClass<*>, Any?> = ListEntry()
            for (i in 0..30) {
                when (Random.nextInt(0, 14)) {
                    1 -> {
                        datas.add(
                            UByteArray::class,
                            UByteArray(Random.nextInt(1, 50)) { index ->
                                Random.nextInt().toUByte()
                            })
                    }
                    2 -> {
                        datas.add(
                            String::class,
                            UByteArray(Random.nextInt(1, 50)) { index ->
                                Random.nextInt().toUByte()
                            }.toStringHex()
                        )
                    }
                    3 -> {
                        datas.add(Int::class, Random.nextInt())
                    }
                    4 -> {
                        datas.add(Long::class, Random.nextLong())
                    }
                    5 -> {
                        datas.add(Boolean::class, Random.nextBoolean())
                    }
                    6 -> {
                        datas.add(UtilsNull.NULL::class, false)
                    }
                    7 -> {
                        datas.add(UtilsNull.NOT_NULL::class, true)
                    }
                    8 -> {
                        datas.add(Float::class, Random.nextFloat())
                    }
                    9 -> {
                        datas.add(Double::class, Random.nextDouble())
                    }
                    10 -> {
                        datas.add(UByte::class, Random.nextInt().toUByte())
                    }
                    11 -> {
                        datas.add(
                            CharArray::class,
                            UByteArray(Random.nextInt(1, 50)) { index ->
                                Random.nextInt().toUByte()
                            }.toStringChar().toCharArray()
                        )
                    }
                    12 -> {
                        datas.add(
                            ByteBuffer::class,
                            UByteArray(Random.nextInt(1, 50)) { index ->
                                Random.nextInt().toUByte()
                            }
                        )
                    }
                    13 -> {
                        datas.add(
                            ByteBuffer::class,
                            Random.nextInt().toUByte()
                        )
                    }


                }
            }
            // build length ByteBuffer
            val length: Int = datas.map {
                val type = it.key
                val value = it.value
                when (type) {
                    UByteArray::class -> {
                        ByteBuffer.BYTES_SIZE(value as UByteArray)
                    }
                    String::class -> {
                        ByteBuffer.STRING_SIZE(value as String)
                    }
                    Int::class -> {
                        ByteBuffer.INT_SIZE()
                    }
                    Long::class -> {

                        ByteBuffer.LONG_SIZE()
                    }
                    Boolean::class -> {

                        ByteBuffer.BOOLEAN_SIZE()
                    }
                    UtilsNull.NULL::class -> {

                        ByteBuffer.FLAG_SIZE()
                    }
                    UtilsNull.NOT_NULL::class -> {

                        ByteBuffer.FLAG_SIZE()
                    }
                    Float::class -> {

                        ByteBuffer.FLOAT_SIZE()
                    }
                    Double::class -> {

                        ByteBuffer.DOUBLE_SIZE()
                    }
                    UByte::class -> {

                        ByteBuffer.BYTE_SIZE()
                    }
                    CharArray::class -> {
                        ByteBuffer.CHARS_SIZE(value as CharArray)
                    }
                    ByteBuffer::class -> {
                        value?.let {
                            when (it::class) {
                                UByteArray::class -> {
                                    (value as UByteArray).size
                                }
                                UByte::class -> {
                                    1
                                }
                                else -> {
                                    0
                                }
                            }
                        } ?: 0
                    }
                    else -> {
                        0
                    }
                }

            }.sum()
            val bytebuffer = ByteBuffer.obtain(length)
            val bytebufferbuilder = ByteBufferBuilder.obtain()
            // build ByteBuffer and // build ByteBufferBuilder
            datas.forEach {
                val type = it.key
                val value = it.value
                when (type) {
                    UByteArray::class -> {
                        bytebuffer.bytes = value as UByteArray
                        bytebufferbuilder.bytes = value
                    }
                    String::class -> {
                        bytebuffer.string = value as String
                        bytebufferbuilder.string = value
                    }
                    Int::class -> {
                        bytebuffer.int = value as Int
                        bytebufferbuilder.int = value
                    }
                    Long::class -> {
                        bytebuffer.long = value as Long
                        bytebufferbuilder.long = value
                    }
                    Boolean::class -> {
                        bytebuffer.boolean = value as Boolean
                        bytebufferbuilder.boolean = value
                    }
                    UtilsNull.NULL::class -> {
                        bytebuffer.nullFlag = false
                        bytebufferbuilder.nullFlag = false
                    }
                    UtilsNull.NOT_NULL::class -> {
                        bytebuffer.nullFlag = true
                        bytebufferbuilder.nullFlag = true
                    }
                    Float::class -> {
                        bytebuffer.float = value as Float
                        bytebufferbuilder.float = value
                    }
                    Double::class -> {
                        bytebuffer.double = value as Double
                        bytebufferbuilder.double = value
                    }
                    UByte::class -> {
                        bytebuffer.byte = value as UByte
                        bytebufferbuilder.byte = value
                    }
                    CharArray::class -> {
                        bytebuffer.chars = value as CharArray
                        bytebufferbuilder.chars = value
                    }
                    ByteBuffer::class -> {
                        value?.let {
                            when (it::class) {
                                UByteArray::class -> {
                                    bytebuffer.writeToBuffer(value as UByteArray)
                                    bytebufferbuilder.writeToBuffer(value)
                                }
                                UByte::class -> {
                                    bytebuffer.writeToBuffer(value as UByte)
                                    bytebufferbuilder.writeToBuffer(value)
                                }
                            }
                        }
                    }
                }
            }

            // verify
            Truth.assertWithMessage("failed after $i").that(bytebuffer.uByteArray)
                .containsExactlyElementsIn(bytebufferbuilder.build().uByteArray).inOrder()
        }
    }
}