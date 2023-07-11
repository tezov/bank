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
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toDouble
import com.tezov.lib_core_kotlin.type.primitive.DoubleTo.toUByteArray
import com.tezov.lib_core_kotlin.type.primitive.string.StringHexTo.toUByteArrayHex
import com.tezov.test_common.rule.RepeatRule
import com.tezov.test_common.rule.RepeatTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class DoubleToTest {


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
    fun double_to_array_to_double_is_conform() {
        val result = "406649999999999A".toUByteArrayHex()
        val value = 178.3.toUByteArray()
        Truth.assertThat(value).containsExactlyElementsIn(result).inOrder()
    }

    @RepeatTest(LOOP)
    @Test
    fun random_double_to_array_to_double_is_conform() {
        val result = Random.nextDouble()
        val value = result.toUByteArray()
        Truth.assertThat(value.toDouble()).isEqualTo(result)
    }

}