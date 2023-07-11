/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.block

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tezov.lib_core_android_kotlin.ui.modifier.fillMaxHeightRemembered
import com.tezov.lib_core_android_kotlin.ui.modifier.thenOnTrue
import com.tezov.lib_core_android_kotlin.ui.theme.style.*
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitShape.StateColor.Style.Companion.asStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack

object HorizontalRoller {

    object Simple {

        open class StyleBuilder internal constructor(style: Style) {
            var spacingItem = style.spacingItem
            var paddingContent = style.paddingContent
            var heightItemToHighest = style.heightItemToHighest

            internal open fun get() = Style(
                spacingItem = spacingItem,
                paddingContent = paddingContent,
                heightItemToHighest = heightItemToHighest,
            )
        }

        open class Style(
            spacingItem: Dp? = null,
            paddingContent: PaddingValues? = null,
            heightItemToHighest: Boolean? = null,
        ) {

            val spacingItem: Dp by DelegateNullFallBack.Ref(
                spacingItem,
                fallBackValue = { 0.dp })
            val paddingContent: PaddingValues by DelegateNullFallBack.Ref(
                paddingContent,
                fallBackValue = { PaddingValues() })
            val heightItemToHighest: Boolean by DelegateNullFallBack.Ref(
                heightItemToHighest,
                fallBackValue = { false })

            companion object {

                @Composable
                fun Style.copy(scope: @Composable StyleBuilder.() -> Unit) =
                    StyleBuilder(this).also {
                        it.scope()
                    }.get()

            }

            constructor(style: Style?) : this(
                paddingContent = style?.paddingContent,
                spacingItem = style?.spacingItem,
                heightItemToHighest = style?.heightItemToHighest,
            )
        }

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: Style,
            itemSelected: Int = 0,
            items: List<@Composable LazyItemScope.() -> Unit>,
        ) {
            LazyRow(
                modifier = modifier
                    .thenOnTrue(style.heightItemToHighest) {
                        fillMaxHeightRemembered()
                    },
                contentPadding = style.paddingContent,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(style.spacingItem)
            ) {
                itemsIndexed(items) { index, item ->
                    item(this)
                }
            }
//            TODO scroll first selected
//            LaunchedEffect(Unit) {
//                pagerState.scrollToPage(itemSelected)
//            }
        }
    }

    object Card {

        class StyleBuilder internal constructor(style: Style) : Simple.StyleBuilder(style) {
            var outfitFrame = style.outfitFrame
            var marginCard = style.marginCard

            override fun get() = Style(
                stylePager = super.get(),
                outfitFrame = outfitFrame,
                marginCard = marginCard,
            )
        }

        class Style(
            stylePager: Simple.Style? = null,
            outfitFrame: OutfitFrameStateColor? = null,
            marginCard: PaddingValues = PaddingValues(horizontal = 4.dp),
        ) : Simple.Style(stylePager) {

            val outfitFrame: OutfitFrameStateColor by DelegateNullFallBack.Ref(
                outfitFrame,
                fallBackValue = {
                    OutfitFrameStateColor(
                        outfitShape = 8.asStateColor,
                        outfitBorder = OutfitBorderStateColor(
                            size = 1.dp,
                            outfitState = Color.Black.asStateSimple
                        ),
                    )
                }
            )
            val marginCard: PaddingValues by DelegateNullFallBack.Ref(
                marginCard,
                fallBackValue = { PaddingValues() }
            )

            companion object {

                @Composable
                fun Style.copy(scope: @Composable StyleBuilder.() -> Unit) =
                    StyleBuilder(this).also {
                        it.scope()
                    }.get()

                @Composable
                fun Simple.Style.copyToCardStyle(scope: @Composable StyleBuilder.() -> Unit) =
                    StyleBuilder(Style(this)).also { it.scope() }.get()

            }

            constructor(style: Style) : this(
                stylePager = style,
                outfitFrame = style.outfitFrame,
                marginCard = style.marginCard,
            )
        }

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: Style,
            itemSelected: Int = 0,
            items: List<@Composable LazyItemScope.() -> Unit>,
        ) {
            Simple(modifier, style, itemSelected, items.map { content ->
                {
                    Surface(
                        shape = style.outfitFrame.getShape() ?: RectangleShape,
                        border = style.outfitFrame.resolveBorder(),
                        color = style.outfitFrame.resolveColorShape()
                            ?: MaterialTheme.colors.surface,
                    ) {
                        content()
                    }
                }
            })
        }
    }

}