/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.type

import androidx.compose.runtime.*
import kotlin.reflect.KProperty

fun <T> mutableStaticStateOf(
    value: T
): MutableStaticState<T> = object : MutableStaticState<T> {
    override var value: T = value
}

@Stable
interface MutableStaticState<T> {
    var value: T
}
