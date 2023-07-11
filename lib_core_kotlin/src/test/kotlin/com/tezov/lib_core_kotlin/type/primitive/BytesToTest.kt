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

package com.tezov.lib_core_kotlin.type.primitive

import android.util.Base64
import com.google.common.truth.Truth
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.complement
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toBoolean
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toDouble
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toFloat
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toInt
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toLong
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringBase49
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringBase58
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringBase64
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringChar
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringHex
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringHexChar
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.uByteArrayOf
import com.tezov.lib_core_kotlin.type.primitive.string.StringHexTo.toUByteArrayHex
import com.tezov.test_common.rule.RepeatRule
import com.tezov.test_common.rule.RepeatTest
import io.mockk.clearStaticMockk
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigInteger
import kotlin.properties.Delegates
import kotlin.random.Random
import kotlin.random.nextUBytes
import kotlin.random.nextUInt

//TODO verifier aussi accent carrectéres speciaux? same crypter application
//test avec offset aussi
//faire tous les test reverses primitive comme CharsToTest

class BytesToTest {

    companion object {
        const val LOOP = 50
    }

    @get:Rule
    val repeatRule = RepeatRule()

    var uByteArrayOfDirectOrderHexa by Delegates.notNull<UByteArray>()

    @Before
    fun setup() {
        //require success StringHexToTest.toUByteArrayHex_isConform()
        uByteArrayOfDirectOrderHexa =
            "000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F202122232425262728292A2B2C2D2E2F303132333435363738393A3B3C3D3E3F404142434445464748494A4B4C4D4E4F505152535455565758595A5B5C5D5E5F606162636465666768696A6B6C6D6E6F707172737475767778797A7B7C7D7E7F808182838485868788898A8B8C8D8E8F909192939495969798999A9B9C9D9E9FA0A1A2A3A4A5A6A7A8A9AAABACADAEAFB0B1B2B3B4B5B6B7B8B9BABBBCBDBEBFC0C1C2C3C4C5C6C7C8C9CACBCCCDCECFD0D1D2D3D4D5D6D7D8D9DADBDCDDDEDFE0E1E2E3E4E5E6E7E8E9EAEBECEDEEEFF0F1F2F3F4F5F6F7F8F9FAFBFCFDFEFF".toUByteArrayHex()

    }

    @After
    fun teardown() {
        uByteArrayOfDirectOrderHexa = UByteArray(0)
    }

    @RepeatTest(LOOP)
    @Test
    fun toStringHex_isConform() {
        val value = Random.nextUBytes(0xFF)
        val result = StringBuilder()
        value.forEach {
            result.append(it.toString(16).padStart(2, '0').uppercase())
        }
        Truth.assertThat(value.toStringHex())
            .isEqualTo(result.toString())

    }

    @RepeatTest(LOOP)
    @Test
    fun toStringChar_isConform() {
        val value = UByteArray(0xFF)
        for (j in value.indices) {
            value[j] = Random.nextUInt(0x20U, 0x7EU).toUByte()
        }
        val result = StringBuilder()
        value.forEach {
            result.append(it.toInt().toChar())
        }
        Truth.assertThat(value.toStringChar()).isEqualTo(result.toString())
    }

    @Test
    fun toStringHexChar_isConform() {
        val result =
            "AAABACADAEAFAGAHAIAJAKALAMANAOAPBABBBCBDBEBFBGBHBIBJBKBLBMBNBOBPCACBCCCDCECFCGCHCICJCKCLCMCNCOCPDADBDCDDDEDFDGDHDIDJDKDLDMDNDODPEAEBECEDEEEFEGEHEIEJEKELEMENEOEPFAFBFCFDFEFFFGFHFIFJFKFLFMFNFOFPGAGBGCGDGEGFGGGHGIGJGKGLGMGNGOGPHAHBHCHDHEHFHGHHHIHJHKHLHMHNHOHPIAIBICIDIEIFIGIHIIIJIKILIMINIOIPJAJBJCJDJEJFJGJHJIJJJKJLJMJNJOJPKAKBKCKDKEKFKGKHKIKJKKKLKMKNKOKPLALBLCLDLELFLGLHLILJLKLLLMLNLOLPMAMBMCMDMEMFMGMHMIMJMKMLMMMNMOMPNANBNCNDNENFNGNHNINJNKNLNMNNNONPOAOBOCODOEOFOGOHOIOJOKOLOMONOOOPPAPBPCPDPEPFPGPHPIPJPKPLPMPNPOPP"
        Truth.assertThat(uByteArrayOfDirectOrderHexa.toStringHexChar()).isEqualTo(result)
    }

    @Test
    fun toStringBase64_isConform() {
        mockkStatic(Base64::class) {
            every {
                Base64.encodeToString(any(), any())
            } answers {
                val input = this.args[0] as ByteArray
                val mode = this.args[1] as Int
                java.util.Base64.getEncoder().encodeToString(input)
            }
            val result =
                "AAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDEyMzQ1Njc4OTo7PD0+P0BBQkNERUZHSElKS0xNTk9QUVJTVFVWV1hZWltcXV5fYGFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6e3x9fn+AgYKDhIWGh4iJiouMjY6PkJGSk5SVlpeYmZqbnJ2en6ChoqOkpaanqKmqq6ytrq+wsbKztLW2t7i5uru8vb6/wMHCw8TFxsfIycrLzM3Oz9DR0tPU1dbX2Nna29zd3t/g4eLj5OXm5+jp6uvs7e7v8PHy8/T19vf4+fr7/P3+/w=="
            Truth.assertThat(uByteArrayOfDirectOrderHexa.toStringBase64()).isEqualTo(result)
        }
        clearStaticMockk(Base64::class)
    }

    @Test
    fun toStringBase58_isConform() {
        val result =
            "1cWB5HCBdLjAuqGGReWE3R3CguuwSjw6RHn39s2yuDRTS5NsBgNiFpWgAnEx6VQi8csexkgYw3mdYrMHr8x9i7aEwP8kZ7vccXWqKDvGv3u1GxFKPuAkn8JCPPGDMf3vMMnbzm6Nh9zh1gcNsMvH3ZNLmP5fSG6DGbbi2tuwMWPthr4boWwCxf7ewSgNQeacyozhKDDQQ1qL5fQFUW52QKUZDZ5fw3KXNQJMcNTcaB723LchjeKun7MuGW5qyCBZYzA1KjofN1gYBV3NqyhQJ3Ns746GNuf9N2pQPmHz4xpnSrrfCvy6TVVz5d4PdrjeshsWQwpZsZGzvbdAdN8MKV5QsBDY"
        Truth.assertThat(uByteArrayOfDirectOrderHexa.toStringBase58()).isEqualTo(result)
    }

    @Test
    fun toStringBase49_isConform() {
        val result =
            "AkRnsbJEMiLtxvMTzNjikqumzdDGGEyMZknuTeBZqmyQMnnrfTgcKudLQhpRBotirgbnpgkyZbuBMftXnakraMWYwwGYgqjmoyuvhbYSdREQAkETGkiYnzqBFVeBHpNByGoGjTUbdSqhYfPkexDBFPuCRwdyaeXzRqTQrfLAUwpDHsZqFttFzSayeWAasDYAaQPYoTWEhYcfxTqtqQfeNHoJCMQvNxYhphEytaUyESByJTnGnCKRwMUjdPkFUKQxVyzBJNxKwgaysHzZjbxTsRggNtFbtyXAPYPCJttDASXPJtoSHEhPSDQybtZeQRpyqcccjCRPdRUtWfBQUUZmwkwbFXzBWNuCGTMREUfLuCP"
        Truth.assertThat(uByteArrayOfDirectOrderHexa.toStringBase49()).isEqualTo(result)
    }

    @Test
    fun complement_isConform() {
        val result =
            "FFFEFDFCFBFAF9F8F7F6F5F4F3F2F1F0EFEEEDECEBEAE9E8E7E6E5E4E3E2E1E0DFDEDDDCDBDAD9D8D7D6D5D4D3D2D1D0CFCECDCCCBCAC9C8C7C6C5C4C3C2C1C0BFBEBDBCBBBAB9B8B7B6B5B4B3B2B1B0AFAEADACABAAA9A8A7A6A5A4A3A2A1A09F9E9D9C9B9A999897969594939291908F8E8D8C8B8A898887868584838281807F7E7D7C7B7A797877767574737271706F6E6D6C6B6A696867666564636261605F5E5D5C5B5A595857565554535251504F4E4D4C4B4A494847464544434241403F3E3D3C3B3A393837363534333231302F2E2D2C2B2A292827262524232221201F1E1D1C1B1A191817161514131211100F0E0D0C0B0A09080706050403020100".toUByteArrayHex()
        Truth.assertThat(uByteArrayOfDirectOrderHexa.complement()).containsExactlyElementsIn(result)
            .inOrder()
    }

    @RepeatTest(LOOP)
    @Test
    fun toLong_isConform() {

        val value = UByteArray(LongTo.BYTES)
        for (j in value.indices) {
            value[j] = Random.nextUInt().toUByte()
        }
        val result = BigInteger(value.toByteArray()).toLong()
        Truth.assertThat(value.toLong()).isEqualTo(result)

    }

    @RepeatTest(LOOP)
    @Test
    fun toInt_isConform() {

        val value = UByteArray(IntTo.BYTES)
        for (j in value.indices) {
            value[j] = Random.nextUInt().toUByte()
        }
        val result = BigInteger(value.toByteArray()).toInt()
        Truth.assertThat(value.toInt()).isEqualTo(result)

    }

    @RepeatTest(LOOP)
    @Test
    fun toFloat_isConform() {

        val value = UByteArray(FloatTo.BYTES)
        for (j in value.indices) {
            value[j] = Random.nextUInt().toUByte()
        }
        val result = Float.fromBits(value.toInt())
        Truth.assertThat(value.toFloat()).isEqualTo(result)

    }

    @RepeatTest(LOOP)
    @Test
    fun toDouble_isConform() {

        val value = UByteArray(DoubleTo.BYTES)
        for (j in value.indices) {
            value[j] = Random.nextUInt().toUByte()
        }
        val result = Double.fromBits(value.toLong())
        Truth.assertThat(value.toDouble()).isEqualTo(result)

    }

    @Test
    fun toBoolean_isConform() {
        Truth.assertThat(uByteArrayOf(0x01).toBoolean()).isTrue()
        Truth.assertThat(uByteArrayOf(0x00).toBoolean()).isFalse()
    }

}