/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation.bottom_navigation

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
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Shadow
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalActivity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySub
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.with
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitState
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitState.BiStable.Style.Companion.asStateBiStable
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitStateBiStable
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.theme.ThemeColorsExtended
import com.tezov.lib_core_android_kotlin.ui.theme.theme.componentsCommonExtended
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack

object BottomNavigation :
    ActivitySub<BottomNavigationState, BottomNavigationAction> {

    class StyleBuilder internal constructor(style: Style) {
        var elevation = style.elevation
        var outfitText = style.outfitText
        var colorBackground = style.colorBackground
        var outfitColor = style.outfitColor

        internal fun get() = Style(
            elevation = elevation,
            outfitText = outfitText,
            colorBackground = colorBackground,
            outfitColor = outfitColor,
        )
    }

    class Style(
        val elevation: Dp = 2.dp,
        outfitText: OutfitTextStateColor? = null,
        colorBackground: Color? = null,
        outfitColor: OutfitStateBiStable<Color>? = null,
    ) {

        val outfitText: OutfitTextStateColor by DelegateNullFallBack.Ref(
            outfitText,
            fallBackValue = {
                OutfitTextStateColor()
            })

        val colorBackground: Color by DelegateNullFallBack.Ref(
            colorBackground,
            fallBackValue = {
                ThemeColorsExtended.Dummy.pink
            })

        val outfitColor: OutfitStateBiStable<Color> by DelegateNullFallBack.Ref(
            outfitColor,
            fallBackValue = {
                ThemeColorsExtended.Dummy.green.asStateBiStable
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
            outfitColor = style.outfitColor,
        )
    }

    @Composable
    operator fun invoke(items: Set<BottomNavigationItemData>) {
        content(items)
    }

    @Composable
    private fun content(items: Set<BottomNavigationItemData>) {
        val accessor = DiAccessorCoreUiActivity().with(LocalActivity.current).contextSubMap()
        val action = accessor.with<BottomNavigation, _, _>().action()
        val style = MaterialTheme.componentsCommonExtended.bottomNavigation
        Box {
            BottomNavigation(
                elevation = 0.dp,
                backgroundColor = style.colorBackground,
            ) {
                items.forEach { item ->
                    val isCurrentRoute = action.navigationController.finalRoute()?.path == item.route.path
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(
                                    id = if (isCurrentRoute)
                                        item.iconActive else item.iconInactive
                                ),
                                contentDescription = stringResource(id = item.titleResourceId)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.titleResourceId),
                                style =style.outfitText.typo,
                            )
                        },
                        selectedContentColor = style.outfitColor.resolve(
                            OutfitState.BiStable.Selector.Active
                        ) ?: LocalContentColor.current,
                        unselectedContentColor = style.outfitColor.resolve(
                            OutfitState.BiStable.Selector.Inactive
                        ) ?: LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                        alwaysShowLabel = true,
                        selected = isCurrentRoute,
                        onClick = {
                            action.onClickItem(item.route)
                        }
                    )
                }
            }
            if (style.elevation > 0.dp) {
                Shadow.Line(
                    modifier = Modifier.align(Alignment.TopCenter),
                    elevation = style.elevation,
                )
            }
        }


    }
}