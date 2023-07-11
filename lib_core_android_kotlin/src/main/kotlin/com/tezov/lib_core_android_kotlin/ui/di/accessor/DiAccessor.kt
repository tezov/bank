/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.di.accessor

import android.util.Log
import androidx.compose.runtime.Composable
import com.tezov.lib_core_kotlin.type.collection.ListEntry

abstract class DiAccessor<COMPONENT : Any> {

    interface Key {
        val diAccessorKeyId: Int get() = hashCode()
    }

    private val components: ListEntry<Int, COMPONENT> = ListEntry()

    @Composable
    protected abstract fun create(): COMPONENT

    @Composable
    protected open fun onCreated() {
    }

    @Composable
    fun with(key: Key): COMPONENT {
        return with(key, key)
    }

    @Composable
    open fun with(requester: Any, key: Key): COMPONENT =
        components.getValue(key.diAccessorKeyId) ?: run {
            create().also {
                components.add(key.diAccessorKeyId, it)
                onCreated()
            }
        }

    @Composable
    open fun dispose(requester: Any, key: Key) =
        null != components.removeKey(key.diAccessorKeyId)

}