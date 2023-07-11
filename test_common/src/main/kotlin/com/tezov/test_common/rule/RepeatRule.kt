/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.test_common.rule

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RepeatRule : TestRule {

    private class RepeatStatement(private val statement: Statement, private val repeat: Int) :
        Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            require(repeat >= 1)
            for (i in 1..repeat) {
                statement.evaluate()
            }
        }
    }

    override fun apply(base: Statement, description: Description) = description.getAnnotation(
        RepeatTest::class.java
    )?.let {
        RepeatStatement(base, it.value)
    } ?: let {
        base
    }
}