/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.di.common

import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.ui.compositionTree.base.Composition
import com.tezov.lib_core_android_kotlin.ui.compositionTree.base.CompositionAction
import com.tezov.lib_core_android_kotlin.ui.compositionTree.base.CompositionState


class ComposableContext<S : CompositionState, A : CompositionAction<S>>(
    internal val _state: ComposableWrapper<S>,
    internal val _action: ComposableWrapper<A>,
) {
    @Composable
    fun remember(canDispose:Boolean = true) {
        _state.remember(canDispose)
        _action.remember(canDispose)
    }

    fun dispose() = _state.dispose() or _action.dispose()
}

class ComposableContextMap(vararg pairs: Pair<Class<out Composition<*, *>>, ComposableContext<*, *>>) {
    val map = pairs.toMap()

    @Composable
    fun remember(canDispose:Boolean = true) {
        map.forEach { it.value.remember(canDispose) }
    }

    fun dispose() = map.values.fold(false) { acc, value -> acc or value.dispose() }
}

