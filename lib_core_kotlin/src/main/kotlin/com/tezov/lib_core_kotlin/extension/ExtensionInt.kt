/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.extension


object ExtensionInt {

    fun Int.isEven() = this % 2 == 0

    fun <T> Int.onEven(run: (Int) -> T) = if (isEven()) {
        run(this)
    } else null

    fun Int.isOdd() = this % 2 == 1
    fun <T> Int.onOdd(run: (Int) -> T) = if (isOdd()) {
        run(this)
    } else null

    fun <T> Int.action(ifEven: (Int) -> T, ifOdd: (Int) -> T) = if (isEven()) {
        ifEven(this)
    } else {
        ifOdd(this)
    }
}