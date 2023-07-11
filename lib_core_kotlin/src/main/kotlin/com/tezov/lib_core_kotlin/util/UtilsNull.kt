/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.util

object UtilsNull {

    object NULL
    object NOT_NULL

    inline fun <U : Any, V : Any, R : Any> ifNotNull(
        first: U?,
        second: V?,
        action: (U, V) -> R
    ): R? {
        return when {
            first != null && second != null -> action(first, second)
            else -> null
        }
    }

    inline fun <U : Any, V : Any, W : Any, R : Any> ifNotNull(
        first: U?,
        second: V?,
        third: W?,
        action: (U, V, W) -> R
    ): R? {
        return when {
            first != null && second != null && third != null -> action(first, second, third)
            else -> null
        }
    }

    inline fun <U : Any, V : Any, W : Any, X : Any, R : Any> ifNotNull(
        first: U?,
        second: V?,
        third: W?,
        fourth: X?,
        action: (U, V, W, X) -> R
    ): R? {
        return when {
            first != null && second != null && third != null && fourth != null -> action(
                first,
                second,
                third,
                fourth
            )
            else -> null
        }
    }

}