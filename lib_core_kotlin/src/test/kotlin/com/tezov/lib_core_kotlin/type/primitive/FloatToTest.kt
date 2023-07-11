/*
 *  ********************************************************************************
 *  Created by Tezov under MIT LICENCE.
 *  For any request, please send me an email to tezov.app@gmail.com.
 *  I'll be glad to answer you if your request is sane :)
 *  Last update 2023
 *  ********************************************************************************
 */

package com.tezov.lib_core_kotlin.type.primitive

import com.google.common.truth.Truth
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toFloat
import com.tezov.lib_core_kotlin.type.primitive.FloatTo.toUByteArray
import com.tezov.lib_core_kotlin.type.primitive.string.StringHexTo.toUByteArrayHex
import com.tezov.test_common.rule.RepeatRule
import com.tezov.test_common.rule.RepeatTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class FloatToTest {

    companion object {
        const val LOOP = 1000
    }

    @get:Rule
    val repeatRule = RepeatRule()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun float_to_array_to_float_is_conform() {
        val result = "43324CCD".toUByteArrayHex()
        val value = 178.3F.toUByteArray()
        Truth.assertThat(value).containsExactlyElementsIn(result).inOrder()
    }

    @RepeatTest(LOOP)
    @Test
    fun random_float_to_array_to_float_is_conform() {
        val result = Random.nextFloat()
        val value = result.toUByteArray()
        Truth.assertThat(value.toFloat()).isEqualTo(result)
    }
}