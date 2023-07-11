/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation.top_app_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Icon
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Shadow
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Text
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalActivity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySub
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.with
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.theme.ThemeColorsExtended
import com.tezov.lib_core_android_kotlin.ui.theme.theme.componentsCommonExtended
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack

object TopAppBar : ActivitySub<TopAppBarState, TopAppBarAction> {

    class StyleBuilder internal constructor(style: Style) {
        var elevation = style.elevation
        var outfitText = style.outfitText
        var colorBackground = style.colorBackground
        var colorIconStart = style.colorIconStart
        var colorIconEnd = style.colorIconEnd

        internal fun get() = Style(
            elevation = elevation,
            outfitText = outfitText,
            colorBackground = colorBackground,
            colorIconStart = colorIconStart,
            colorIconEnd = colorIconEnd,
        )
    }

    class Style(
        val elevation: Dp = 2.dp,
        outfitText: OutfitTextStateColor? = null,
        val colorBackground: Color = ThemeColorsExtended.Dummy.pink,
        val colorIconStart: Color = ThemeColorsExtended.Dummy.green,
        val colorIconEnd: Color = ThemeColorsExtended.Dummy.green,
    ) {

        val outfitText: OutfitTextStateColor by DelegateNullFallBack.Ref(
            outfitText,
            fallBackValue = {
                OutfitTextStateColor()
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
            outfitText = style.outfitText,
            colorBackground = style.colorBackground,
            colorIconStart = style.colorIconStart,
            colorIconEnd = style.colorIconEnd,
        )
    }

    @Composable
    operator fun invoke(
        titleResourceId: Int,
        leadingItem: TopAppBarItemData? = null,
        trailingItem: TopAppBarItemData? = null
    ) {
        content(titleResourceId, leadingItem, trailingItem)
    }

    @Composable
    private fun content(
        titleResourceId: Int,
        leadingItem: TopAppBarItemData? = null,
        trailingItem: TopAppBarItemData? = null
    ) {
        val accessor = DiAccessorCoreUiActivity().with(LocalActivity.current).contextSubMap()
        val action = accessor.with<TopAppBar, _, _>().action()
        Box {
            val style = MaterialTheme.componentsCommonExtended.topAppBar
            TopAppBar(
                elevation = 0.dp,
                title = {
                    Text.StateColor(
                        text = stringResource(id = titleResourceId),
                        style = style.outfitText,
                    )
                },
                backgroundColor = style.colorBackground,
                navigationIcon = {
                    leadingItem?.let {
                        Icon.Clickable(
                            colorRipple = style.colorIconStart,
                            onClick = {
                                action.onClickIconButton(it.route)
                            }) {
                            Icon(
                                painterResource(id = it.icon),
                                null,
                                tint = style.colorIconStart
                            )
                        }
                    }
                },
                actions = {
                    trailingItem?.let {
                        Icon.Clickable(
                            colorRipple = style.colorIconEnd,
                            onClick = {
                                action.onClickIconButton(it.route)

                            }) {
                            Icon(
                                painterResource(id = it.icon),
                                null,
                                tint = style.colorIconEnd
                            )
                        }
                    }
                }
            )
            if (style.elevation > 0.dp) {
                Shadow.Line(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    elevation = style.elevation,
                )
            }
        }

    }

}