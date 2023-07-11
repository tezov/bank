/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.compositionTree.modal

import androidx.compose.runtime.*
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalLevel
import com.tezov.lib_core_android_kotlin.ui.compositionTree.base.Composition
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page.Companion.LocalModalsBundle
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page.Companion.LocalPage
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessor
import com.tezov.lib_core_kotlin.extension.ExtensionList.push

interface Modal<S : ModalState, A : ModalAction<S>> : Composition<S, A>, DiAccessor.Key {

    override val diAccessorKeyId: Int
        get() = Modal.hashCode()

    companion object {
        val LocalModalBundle: ProvidableCompositionLocal<Bundle> = staticCompositionLocalOf {
            error("not provided")
        }
        val LocalModal @Composable get() = LocalModalBundle.current

        data class Bundle(
            val current: Modal<*, *>,
        )

        @Composable
        private fun CompositionLocalProvider(canDispose:Boolean, modal:Modal<*,*>, content: @Composable () -> Unit) {
            val modals = LocalModalsBundle
            val bundle = modals.find { it.current == modal } ?: kotlin.run {
                Bundle(modal).also { modals.push(it) }
            }
            CompositionLocalProvider(
                LocalLevel provides 2,
                LocalModalBundle provides bundle,
                content = content
            )
            if(canDispose){
                DisposableEffect(Unit) {
                    onDispose {
                        modals.find { it.current == modal }?.also { modals.remove(it) }
                    }
                }
            }
        }

        @Composable
        fun CompositionLocalProvider( modal:Modal<*,*>, content: @Composable () -> Unit) = CompositionLocalProvider(
            canDispose = false,
            modal = modal,
            content = content)

    }

    @Composable
    operator fun invoke(){
        CompositionLocalProvider(canDispose = true, modal = this) {
            backPressListen()
            content()
        }
    }

    @Composable
    fun Modal<S, A>.content()

    @Composable
    override fun onBackPressedDispatch() {
        if(!onBackButtonPressed()){
            LocalPage.current.onBackPressedDispatch()
        }
    }

    @Composable
    fun onBackButtonPressed() = false

    @Composable
    fun onExit(request: NavigationController.Request) {
    }

}