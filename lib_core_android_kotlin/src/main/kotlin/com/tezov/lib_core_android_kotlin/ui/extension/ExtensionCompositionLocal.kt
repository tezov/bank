/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue

object ExtensionCompositionLocal {

    @Composable
    fun CompositionLocalProvider(
        ancestor: Array<ProvidedValue<*>>,
        parent: @Composable () -> Array<ProvidedValue<*>> = { emptyArray() },
        child: @Composable () -> Array<ProvidedValue<*>> = { emptyArray() },
        content: @Composable () -> Unit
    ) {
        androidx.compose.runtime.CompositionLocalProvider(values = ancestor) {
            androidx.compose.runtime.CompositionLocalProvider(values = parent()) {
                androidx.compose.runtime.CompositionLocalProvider(
                    values = child(),
                    content = content
                )
            }
        }
    }
}