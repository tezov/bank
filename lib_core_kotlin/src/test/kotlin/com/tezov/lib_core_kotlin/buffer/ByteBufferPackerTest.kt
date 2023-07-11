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
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringHex
import com.tezov.lib_core_kotlin.type.primitive.string.StringHexTo.toUByteArrayHex
import org.junit.After
import org.junit.Before
import org.junit.Test

//TODO randomize
class ByteBufferPackerTest {

    companion object {
        const val LOOP = 50
    }


    @Before
    fun setUp() {
        //require success StringHexToTest.toUByteArrayHex_isConform()

    }

    @After
    fun tearDown() {
    }

    @Test
    fun nibbleinputstream_even_isConform() {
        val data = "00112233445566778899AABBCCDDEEFF"
        val input = ByteBufferPacker.NibbleInputStream(data.toUByteArrayHex(), isOdd = false)
        var count = 0
        data.forEach {
            Truth.assertThat(input.isEven).isEqualTo(count % 2 == 0)
            Truth.assertThat(input.isOdd).isEqualTo(count % 2 == 1)
            val result = it.toString().toInt(16).toUByte()
            val read = input.read()
            count++
            Truth.assertThat(input.isEven).isEqualTo(count % 2 == 0)
            Truth.assertThat(input.isOdd).isEqualTo(count % 2 == 1)
            Truth.assertThat(input.available()).isEqualTo(data.length - count)
            Truth.assertThat(read).isEqualTo(result)
        }
    }

    @Test
    fun nibbleinputstream_odd_isConform() {
        val data = "0112233445566778899AABBCCDDEEFF"
        val input = ByteBufferPacker.NibbleInputStream(data.toUByteArrayHex(), isOdd = true)
        var count = 0
        data.forEach {
            Truth.assertThat(input.isEven).isEqualTo(count % 2 == 1)
            Truth.assertThat(input.isOdd).isEqualTo(count % 2 == 0)
            val result = it.toString().toInt(16).toUByte()
            val read = input.read()
            count++
            Truth.assertThat(input.isEven).isEqualTo(count % 2 == 1)
            Truth.assertThat(input.isOdd).isEqualTo(count % 2 == 0)
            Truth.assertThat(input.available()).isEqualTo(data.length - count)
            Truth.assertThat(read).isEqualTo(result)
        }
    }

    @Test
    fun nibbleoutputstream_even_isConform() {
        val data = "00112233445566778899AABBCCDDEEFF"
        val input = ByteBufferPacker.NibbleOutputStream()
        var count = 0
        data.forEach {
            val result = it.toString().toInt(16).toUByte()
            Truth.assertThat(input.isEven).isEqualTo(count % 2 == 0)
            Truth.assertThat(input.isOdd).isEqualTo(count % 2 == 1)
            input.write(result)
            count++
            Truth.assertThat(input.isEven).isEqualTo(count % 2 == 0)
            Truth.assertThat(input.isOdd).isEqualTo(count % 2 == 1)
        }
        val dataBytes = input.toBytes()
        Truth.assertThat(dataBytes.size).isEqualTo(data.length / 2)
        Truth.assertThat(dataBytes).containsExactlyElementsIn(data.toUByteArrayHex()).inOrder()
    }

    @Test
    fun nibbleoutputstream_odd_isConform() {
        val data = "00112233445566778899AABBCCDDEEF"
        val input = ByteBufferPacker.NibbleOutputStream()
        var count = 0
        data.forEach {
            val result = it.toString().toInt(16).toUByte()
            Truth.assertThat(input.isEven).isEqualTo(count % 2 == 0)
            Truth.assertThat(input.isOdd).isEqualTo(count % 2 == 1)
            input.write(result)
            count++
            Truth.assertThat(input.isEven).isEqualTo(count % 2 == 0)
            Truth.assertThat(input.isOdd).isEqualTo(count % 2 == 1)
        }
        val dataBytes = input.toBytes()
        Truth.assertThat(dataBytes.size).isEqualTo(data.length / 2 + 1)
        Truth.assertThat(dataBytes).containsExactlyElementsIn(data.toUByteArrayHex()).inOrder()
    }

    @Test
    fun nibbleinputstreamstack_single_write_isConform() {
        val data = "00112233445566778899AABBCCDDEEFF"
        val input = ByteBufferPacker.NibbleInputStreamStack(ubyteArrayOf(), isOdd = false)
        data.forEach {
            val result = it.toString().toInt(16).toUByte()
            Truth.assertThat(input.available()).isEqualTo(0)
            input.write(result)
            Truth.assertThat(input.available()).isEqualTo(1)
            val read = input.read()
            Truth.assertThat(input.available()).isEqualTo(0)
            Truth.assertThat(read).isEqualTo(result)
        }
    }

    @Test
    fun nibbleinputstreamstack_full_write_isConform() {
        val data = "00112233445566778899AABBCCDDEEFF"
        val input = ByteBufferPacker.NibbleInputStreamStack(ubyteArrayOf(), isOdd = false)
        Truth.assertThat(input.available()).isEqualTo(0)
        var count = 0
        data.forEach {
            val result = it.toString().toInt(16).toUByte()
            input.write(result)
            count++
            Truth.assertThat(input.available()).isEqualTo(count)
        }
        Truth.assertThat(input.available()).isEqualTo(data.length)
        data.forEach {
            val result = it.toString().toInt(16).toUByte()
            val read = input.read()
            count--
            Truth.assertThat(input.available()).isEqualTo(count)
            Truth.assertThat(read).isEqualTo(result)
        }
        Truth.assertThat(input.available()).isEqualTo(0)
    }

    @Test
    fun pack_isCOnform() {
        val result =
            "05A516680065006C006C006F00200077006F0072006C0064081BB68656C6C6F20776F726C64061CAAAAAAAAAAAAAAAAAAAAA0005081010F001340040080D10409101041C100AAAAAAAAAAAAAAAAAAAA060"
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
        val buffer = ByteBufferBuilder.obtain().apply {
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
        val value = ByteBufferPacker().pack(buffer)
        Truth.assertThat(value.toStringHex()).isEqualTo(result)
    }

    @Test
    fun unpack_isConform() {
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
        val data =
            "05A516680065006C006C006F00200077006F0072006C0064081BB68656C6C6F20776F726C64061CAAAAAAAAAAAAAAAAAAAAA0005081010F001340040080D10409101041C100AAAAAAAAAAAAAAAAAAAA060"
        val value = ByteBufferPacker().unpack(data.toUByteArrayHex())
        val buffer = ByteBuffer.wrap(value!!)
        Truth.assertThat(buffer.chars).isEqualTo(charsData)
        Truth.assertThat(buffer.string).isEqualTo(stringData)
        Truth.assertThat(buffer.bytes).containsExactlyElementsIn(bytesData).inOrder()
        Truth.assertThat(buffer.byte).isEqualTo(byteData)
        Truth.assertThat(buffer.int).isEqualTo(intData)
        Truth.assertThat(buffer.long).isEqualTo(longData)
        Truth.assertThat(buffer.double).isEqualTo(doubleData)
        Truth.assertThat(buffer.float).isEqualTo(floatData)
        Truth.assertThat(buffer.boolean).isTrue()
        Truth.assertThat(buffer.boolean).isFalse()
        Truth.assertThat(buffer.nullFlag).isTrue()
        Truth.assertThat(buffer.nullFlag).isFalse()
        Truth.assertThat(buffer.readFromBuffer(10)).containsExactlyElementsIn(copyBytesData)
            .inOrder()
        Truth.assertThat(buffer.readFromBuffer()).isEqualTo(copyByteData)

    }

}