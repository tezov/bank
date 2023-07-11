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
import com.tezov.lib_core_kotlin.type.primitive.string.StringHexTo.toUByteArrayHex
import com.tezov.test_common.rule.RepeatRule
import com.tezov.test_common.rule.RepeatTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random
import kotlin.random.nextUBytes


class StringHexToTest {

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
    fun toUByteArrayHex_isConform() {
        val result = Random.nextUBytes(0xFF)
        val value = StringBuilder()
        result.forEach {
            value.append(it.toString(16).padStart(2, '0').uppercase())
        }
        Truth.assertThat(value.toString().toUByteArrayHex())
            .containsExactlyElementsIn(result).inOrder()
    }

}