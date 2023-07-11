/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalActivity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySub
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.with
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitShapeStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.background
import com.tezov.lib_core_android_kotlin.ui.theme.theme.*
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack
import kotlinx.coroutines.flow.filter

object BottomSheet : ActivitySub<BottomSheetState, BottomSheetAction> {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    operator fun invoke(content: @Composable () -> Unit) {
        val accessor = DiAccessorCoreUiActivity().with(LocalActivity.current).contextSubMap().with<BottomSheet, _, _>()
        val state = accessor.state()
        val action = accessor.action()
        ModalBottomSheetLayout(
            sheetContentColor = Color.Transparent,
            sheetBackgroundColor = Color.Transparent,
            sheetState = state.bottomSheetState,
            sheetShape = RectangleShape,
            sheetElevation = 0.dp,
            sheetContent = {
                state.content()
                LaunchedEffect(Unit) {
                    snapshotFlow { state.bottomSheetState.currentValue }
                        .filter { it == ModalBottomSheetValue.Hidden }
                        .collect {
                            action.close()
                        }
                }
                LaunchedEffect(state.isVisible) {
                    if (state._isVisible) {
                        state.bottomSheetState.show()
                    } else {
                        state.bottomSheetState.hide()
                    }
                }
            },
            content = content
        )
    }

    object Sheet{

        class StyleBuilder internal constructor(style: Style) {
            var elevation = style.elevation
            var shape = style.outfitShape
            var paddingOuter = style.paddingOuter
            var paddingInner = style.paddingInner

            internal fun get() = Style(
                elevation = elevation,
                paddingOuter = paddingOuter,
                paddingInner = paddingInner,
                outfitShape = shape,
            )
        }

        class Style(
            val elevation: Dp = 2.dp,
            outfitShape: OutfitShapeStateColor? = null,
            paddingOuter: PaddingValues? = null,
            paddingInner: PaddingValues? = null,
        ) {
            val outfitShape: OutfitShapeStateColor by DelegateNullFallBack.Ref(
                outfitShape,
                fallBackValue = {
                    ThemeColorsExtended.Dummy.outfitShapeState
                })
            val paddingOuter: PaddingValues by DelegateNullFallBack.Ref(
                paddingOuter,
                fallBackValue = { PaddingValues(start = 1.dp, end = 1.dp) })
            val paddingInner: PaddingValues by DelegateNullFallBack.Ref(
                paddingInner,
                fallBackValue = { PaddingValues() })

            companion object {

                @Composable
                fun Style.copy(builder: @Composable StyleBuilder.() -> Unit = {}) =
                    StyleBuilder(this).also {
                        it.builder()
                    }.get()

            }

            constructor(style: Style) : this(
                elevation = style.elevation,
                outfitShape = style.outfitShape,
                paddingOuter = style.paddingOuter,
                paddingInner = style.paddingInner,
            )
        }

        //TODO manage selector
        //TODO elevation from style

        @Composable
        operator fun invoke(content: @Composable () -> Unit) {
            val style = MaterialTheme.componentsCommonExtended.bottomSheet
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(style.paddingOuter)
                    .background(style.outfitShape)
                    .padding(style.paddingInner),
            ) {
                content()
            }
        }

    }

}