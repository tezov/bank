/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalActivity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySub
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ComposableContext
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.with
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitFrameStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.theme.*
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack

object Dialog : ActivitySub<DialogState, DialogAction> {

    @Composable
    operator fun invoke() {
        val accessor = DiAccessorCoreUiActivity().with(LocalActivity.current).contextSubMap().with<Dialog, _, _>()
        val state = accessor.state()
        val action = accessor.action()
        if (state.isVisible) {
            Dialog(
                onDismissRequest = { action.close() },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    decorFitsSystemWindows = false,
                )
            ) {
                Page.CompositionLocalProvider { state.content() }
            }
        }
    }

    object Card {

        class StyleBuilder internal constructor(style: Style) {
            var elevation = style.elevation
            var outfitFrame = style.outfitFrame

            internal fun get() = Style(
                elevation = elevation,
                outfitFrame = outfitFrame,
            )
        }

        class Style(
            val elevation: Dp = 2.dp,
            outfitFrame: OutfitFrameStateColor? = null,
        ) {

            val outfitFrame: OutfitFrameStateColor by DelegateNullFallBack.Ref(
                outfitFrame,
                fallBackValue = {
                    ThemeColorsExtended.Dummy.outfitFrameState
                })

            companion object {

                @Composable
                fun Style.copy(builder: @Composable StyleBuilder.() -> Unit = {}) =
                    StyleBuilder(this).also {
                        it.builder()
                    }.get()

            }

            constructor(style: Style) : this(
                elevation = style.elevation,
                outfitFrame = style.outfitFrame,
            )
        }

        //TODO manage selector

        @Composable
        operator fun invoke(content: @Composable () -> Unit) {
            val style = MaterialTheme.componentsCommonExtended.dialogCard
            Surface(
                color = style.outfitFrame.resolveColorShape()
                    ?: MaterialTheme.colors.surface,
                shape = style.outfitFrame.getShape()
                    ?: MaterialTheme.shapes.small,
                elevation = style.elevation,
                border = style.outfitFrame.resolveBorder()
            ) {
                content()
            }
        }

    }

}