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
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toCharArray
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringChar
import com.tezov.lib_core_kotlin.type.primitive.CharsTo.toUByteArray
import com.tezov.test_common.rule.RepeatRule
import com.tezov.test_common.rule.RepeatTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random
import kotlin.random.nextUBytes

class CharsToTest {

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
    fun toUByteArray_Back_toCharArray_isConform() {
        val size = Random.nextInt(1, 500)
        val result = Random.nextUBytes(size).toStringChar().toCharArray()
        val value = result.toUByteArray().toCharArray()
        Truth.assertThat(value).isEqualTo(result)
    }

}