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

object ExtensionCoreUi {

    @Suppress("UNCHECKED_CAST")
    @Composable
    inline fun <reified C : Composition<S, A>, S : CompositionState, A : CompositionAction<S>> ComposableContextMap.with(): ComposableContext<S, A> =
        map[C::class.java] as? ComposableContext<S, A> ?: throw IllegalStateException("context not found in map")

    @Composable
    fun <S : CompositionState> ComposableContext<S, *>.state() =
        this._state.get()

    @Composable
    fun <A : CompositionAction<out CompositionState>> ComposableContext<*, A>.action() =
        this._action.get()

}