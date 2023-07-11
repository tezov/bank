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
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringChar
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import kotlin.random.nextUBytes

//TODO verifier aussi accent carrectéres speciaux? same crypter application
//test wrap avec offset sur byte array to check slice/limit

class ByteBufferTest {

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
    fun canWriteAndReadInt() {
        for (i in 0..LOOP) {
            val result = Random.nextInt()
            val length = ByteBuffer.INT_SIZE()
            val byteBufferInput = ByteBuffer.obtain(length)
            byteBufferInput.int = result
            val byteBufferOutput = ByteBuffer.wrap(byteBufferInput.uByteArray)
            Truth.assertWithMessage("failed after $i").that(byteBufferOutput.int).isEqualTo(result)
        }
    }

    @Test
    fun canWriteAndReadLong() {
        for (i in 0..LOOP) {
            val result = Random.nextLong()
            val length = ByteBuffer.LONG_SIZE()
            val byteBufferInput = ByteBuffer.obtain(length)
            byteBufferInput.long = result
            val byteBufferOutput = ByteBuffer.wrap(byteBufferInput.uByteArray)
            Truth.assertWithMessage("failed after $i").that(byteBufferOutput.long).isEqualTo(result)
        }
    }

    @Test
    fun canWriteAndReadFloat() {
        for (i in 0..LOOP) {
            val result = Random.nextFloat()
            val length = ByteBuffer.FLOAT_SIZE()
            val byteBufferInput = ByteBuffer.obtain(length)
            byteBufferInput.float = result
            val byteBufferOutput = ByteBuffer.wrap(byteBufferInput.uByteArray)
            Truth.assertWithMessage("failed after $i").that(byteBufferOutput.float)
                .isEqualTo(result)
        }
    }

    @Test
    fun canWriteAndReadDouble() {
        for (i in 0..LOOP) {
            val result = Random.nextDouble()
            val length = ByteBuffer.DOUBLE_SIZE()
            val byteBufferInput = ByteBuffer.obtain(length)
            byteBufferInput.double = result
            val byteBufferOutput = ByteBuffer.wrap(byteBufferInput.uByteArray)
            Truth.assertWithMessage("failed after $i").that(byteBufferOutput.double)
                .isEqualTo(result)
        }
    }

    @Test
    fun canWriteAndReadBoolean() {
        for (i in 0..LOOP) {
            val result = Random.nextBoolean()
            val length = ByteBuffer.BOOLEAN_SIZE()
            val byteBufferInput = ByteBuffer.obtain(length)
            byteBufferInput.boolean = result
            val byteBufferOutput = ByteBuffer.wrap(byteBufferInput.uByteArray)
            Truth.assertWithMessage("failed after $i").that(byteBufferOutput.boolean)
                .isEqualTo(result)
        }
    }

    @Test
    fun canWriteAndReadByte() {
        for (i in 0..LOOP) {
            val result = Random.nextUBytes(1)[0]
            val length = ByteBuffer.BYTE_SIZE()
            val byteBufferInput = ByteBuffer.obtain(length)
            byteBufferInput.byte = result
            val byteBufferOutput = ByteBuffer.wrap(byteBufferInput.uByteArray)
            Truth.assertWithMessage("failed after $i").that(byteBufferOutput.byte).isEqualTo(result)
        }
    }

    @Test
    fun canWriteAndReadBytes() {
        for (i in 0..LOOP) {
            val size = Random.nextInt(1, 500)
            val result = Random.nextUBytes(size)
            val length = ByteBuffer.BYTES_SIZE(size)
            val byteBufferInput = ByteBuffer.obtain(length)
            byteBufferInput.bytes = result
            val byteBufferOutput = ByteBuffer.wrap(byteBufferInput.uByteArray)
            Truth.assertWithMessage("failed after $i").that(byteBufferOutput.bytes)
                .containsExactlyElementsIn(result).inOrder()
        }
    }

    @Test
    fun canWriteAndReadString() {
        for (i in 0..LOOP) {
            val size = Random.nextInt(1, 500)
            val result = Random.nextUBytes(size).toStringChar()
            val length = ByteBuffer.STRING_SIZE(result)
            val byteBufferInput = ByteBuffer.obtain(length)
            byteBufferInput.string = result
            val byteBufferOutput = ByteBuffer.wrap(byteBufferInput.uByteArray)
            Truth.assertWithMessage("failed after $i").that(byteBufferOutput.string)
                .isEqualTo(result)
        }
    }

    @Test
    fun canWriteAndReadChars() {
        for (i in 0..LOOP) {
            val size = Random.nextInt(1, 500)
            val result = Random.nextUBytes(size).toStringChar().toCharArray()
            val length = ByteBuffer.CHARS_SIZE(result)
            val byteBufferInput = ByteBuffer.obtain(length)
            byteBufferInput.chars = result
            val byteBufferOutput = ByteBuffer.wrap(byteBufferInput.uByteArray)
            Truth.assertWithMessage("failed after $i").that(byteBufferOutput.chars)
                .isEqualTo(result)
        }
    }

}