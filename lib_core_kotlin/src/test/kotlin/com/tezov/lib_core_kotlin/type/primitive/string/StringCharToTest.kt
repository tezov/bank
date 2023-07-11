/*
 *  ********************************************************************************
 *  Created by Tezov under MIT LICENCE.
 *  For any request, please send me an email to tezov.app@gmail.com.
 *  I'll be glad to answer you if your request is sane :)
 *  Last update 2023
 *  ********************************************************************************
 */

package com.tezov.lib_core_kotlin.type.primitive.string

import com.google.common.truth.Truth
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringChar
import com.tezov.lib_core_kotlin.type.primitive.string.StringCharTo.toUByteArrayChar
import com.tezov.lib_core_kotlin.util.UtilsUnicode
import com.tezov.test_common.rule.RepeatRule
import com.tezov.test_common.rule.RepeatTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random
import kotlin.random.nextUInt


class StringCharToTest {

    companion object {
        const val LOOP = 50
    }

    @get:Rule
    val repeatRule = RepeatRule()


    @Before
    fun setup() {

    }

    @After
    fun teardown() {

    }

    @RepeatTest(LOOP)
    @Test
    fun toUByteArrayChar_isConform() {
        val result = UByteArray(0xFF)
        for (j in result.indices) {
            result[j] = Random.nextUInt(0x20U, 0x7EU).toUByte()
        }
        val value = StringBuilder()
        result.forEach {
            value.append(it.toInt().toChar())
        }
        Truth.assertThat(value.toString().toUByteArrayChar()).containsExactlyElementsIn(result)
            .inOrder()
    }


    @Test
    fun toUByteArrayChar_with_latin_isConform() {
        val latin = UtilsUnicode.Latin()
        Truth.assertThat(latin.all().toUByteArrayChar().toStringChar()).isEqualTo(latin.all())
    }


}