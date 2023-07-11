/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.extension

object ExtensionBoolean {

    inline val Boolean?.isTrue get() = this == true

    inline val  Boolean?.isTrueOrNull get()  = isTrue || this == null

    inline val  Boolean?.isFalse get()  = this == false

    inline val  Boolean?.isFalseOrNull get()  = isFalse || this == null

    inline fun <T> Boolean.ifTrue(crossinline run: () -> T) = if (this) {
        run()
    } else null

    inline fun <T> Boolean.ifFalse(crossinline run: () -> T) = if (!this) {
        run()
    } else null

    inline fun <T> Boolean.action(crossinline ifTrue: () -> T, crossinline ifFalse: () -> T) = if (this) {
        ifTrue()
    } else {
        ifFalse()
    }

    fun Boolean.toInt() = if (this) 1 else 0

    fun Boolean?.toInt() = this?.toInt()

}