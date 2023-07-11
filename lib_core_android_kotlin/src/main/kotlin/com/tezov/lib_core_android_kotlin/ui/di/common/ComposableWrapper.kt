/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.di.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNotNull

abstract class ComposableWrapper<T : Any> {
    private var value: T? = null

    @Composable
    fun remember(canDispose:Boolean = true) {
        value ?: run {
            value = create()
            onCreated()
        }
        DisposableEffect(Unit) {
            onDispose {
                if(canDispose){
                    dispose()
                }
            }
        }
    }

    @Composable
    protected abstract fun create(): T

    @Composable
    protected open fun onCreated() {
    }

    @Composable
    fun get(): T = value ?: throw NullPointerException("value not remembered or already disposed")

    fun dispose(): Boolean = if (value.isNotNull) {
        value = null
        true
    } else {
        false
    }

}