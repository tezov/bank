/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.compositionTree.page

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalActivity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalLevel
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalPagesBundle
import com.tezov.lib_core_android_kotlin.ui.compositionTree.base.Composition
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.Modal
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessor
import com.tezov.lib_core_kotlin.extension.ExtensionList.push

interface Page<S : PageState, A : PageAction<S>> : Composition<S, A>, DiAccessor.Key {

    override val diAccessorKeyId: Int
        get() = Page.hashCode()

    companion object {
        val LocalPageBundle: ProvidableCompositionLocal<Bundle> = staticCompositionLocalOf {
            error("not provided")
        }
        val LocalPage @Composable get() = LocalPageBundle.current
        val LocalModalsBundle @Composable get() = LocalPageBundle.current.modals

        data class Bundle(
            val current: Page<*, *>,
        ) {
            val modals by lazy { ArrayDeque<Modal.Companion.Bundle>() }
        }

        @Composable
        private fun CompositionLocalProvider(
            canDispose: Boolean,
            page: Page<*, *>,
            content: @Composable () -> Unit
        ) {
            val pages = LocalPagesBundle
            val bundle = pages.find { it.current == page } ?: kotlin.run {
                Bundle(page).also { pages.push(it) }
            }
            CompositionLocalProvider(
                LocalLevel provides 1,
                LocalPageBundle provides bundle,
                content = content
            )
            if (canDispose) {
                DisposableEffect(Unit) {
                    onDispose {
                        pages.find { it.current == page }?.also { pages.remove(it) }
                    }
                }
            }
        }

        @Composable
        fun CompositionLocalProvider(page: Page<*, *>, content: @Composable () -> Unit) =
            CompositionLocalProvider(
                canDispose = false,
                page = page,
                content = content
            )

        @Composable
        fun CompositionLocalProvider(content: @Composable () -> Unit) = CompositionLocalProvider(
            canDispose = false,
            page = LocalPagesBundle.last().current,
            content = content
        )

    }

    @Composable
    operator fun invoke(graphEntry: GraphEntry, innerPadding: PaddingValues){
        CompositionLocalProvider(canDispose = true, page = this) {
            backPressListen()
            content(graphEntry = graphEntry, innerPadding = innerPadding)
        }
    }

    @Composable
    fun Page<S, A>.content(graphEntry: GraphEntry, innerPadding: PaddingValues)

    @Composable
    override fun onBackPressedDispatch() {
        if (!onBackButtonPressed()) {
            LocalActivity.current.onBackPressedDispatch()
        }
    }

    @Composable
    fun onBackButtonPressed() = false

    @Composable
    fun onExit(request: NavigationController.Request) {
    }

}
