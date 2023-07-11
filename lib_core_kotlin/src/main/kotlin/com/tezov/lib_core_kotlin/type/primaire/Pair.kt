/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.type.primaire

class Pair<F, S>(var first: F, var second: S) {

    override fun equals(other: Any?): Boolean {
        return if (other is Pair<*, *>) {
            var result = equals(first, other.first)
            result = result and equals(second, other.second)
            result
        } else {
            false
        }
    }

    private fun equals(o1: Any?, o2: Any?): Boolean {
        return when {
            o1 != null -> o1 == o2
            else -> o2 == null
        }
    }
}