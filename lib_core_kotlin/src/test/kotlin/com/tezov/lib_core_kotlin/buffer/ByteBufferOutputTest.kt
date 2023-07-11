/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 16:50
 * Last modified 09/07/2023 16:50
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
import org.junit.After
import org.junit.Before
import org.junit.Test

//TODO random test

class ByteBufferOutputTest {

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
    fun bytebufferoutput_is_equal_to_bytebufferbuilder() {
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

        val bytebufferoutput = ByteBufferOutput.obtain().apply {
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

        Truth.assertThat(bytebufferoutput).containsExactlyElementsIn(bytebufferbuilder).inOrder()
    }

}