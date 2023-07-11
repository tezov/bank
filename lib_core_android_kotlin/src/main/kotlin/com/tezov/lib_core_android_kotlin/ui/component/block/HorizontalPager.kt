/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.block

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.tezov.lib_core_android_kotlin.ui.type.primaire.DpSize
import com.tezov.lib_core_android_kotlin.ui.component.block.HorizontalPager.WithTabRow.Style.Companion.INDICATOR_PADDING_VERTICAL
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Text
import com.tezov.lib_core_android_kotlin.ui.modifier.fillMaxHeightRemembered
import com.tezov.lib_core_android_kotlin.ui.modifier.thenOnTrue
import com.tezov.lib_core_android_kotlin.ui.theme.style.*
import com.tezov.lib_core_android_kotlin.ui.theme.theme.*
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack
import com.tezov.lib_core_kotlin.type.collection.ListEntry
import kotlinx.coroutines.launch

object HorizontalPager {

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

        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: Style,
            itemSelected: Int = 0,
            items: List<@Composable () -> Unit>,
            pagerState: PagerState = rememberPagerState {
                items.size
            },
            onItemChange: ((index: Int) -> Unit)? = null
        ) {
            HorizontalPager(
                modifier = modifier
                    .thenOnTrue(style.heightItemToHighest) {
                        fillMaxHeightRemembered()
                    },
                state = pagerState,
                contentPadding = style.paddingContent,
                pageSpacing = style.spacingItem,
            ) { index ->
                items[index]()
            }
            onItemChange?.let {
                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.currentPage }.collect { index ->
                        onItemChange(index)
                    }
                }
            }
            LaunchedEffect(Unit) {
                pagerState.scrollToPage(itemSelected)
            }
        }
    }

    object WithIndicator {

        class StyleBuilder internal constructor(style: Style) : Simple.StyleBuilder(style) {
            var outfitShapeIndicator = style.outfitShapeIndicator
            var sizeIndicator = style.sizeIndicator
            var paddingTopIndicator = style.paddingTopIndicator
            var spacingIndicator = style.spacingIndicator

            override fun get() = Style(
                stylePager = super.get(),
                outfitShapeIndicator = outfitShapeIndicator,
                sizeIndicator = sizeIndicator,
                paddingTopIndicator = paddingTopIndicator,
                spacingIndicator = spacingIndicator,
            )
        }

        class Style(
            stylePager: Simple.Style? = null,
            outfitShapeIndicator: OutfitShapeStateColor? = null,
            paddingTopIndicator: Dp? = null,
            sizeIndicator: Dp? = null,
            spacingIndicator: Dp? = null,
        ) : Simple.Style(stylePager) {

            val outfitShapeIndicator: OutfitShapeStateColor by DelegateNullFallBack.Ref(
                outfitShapeIndicator,
                fallBackValue = {
                    ThemeColorsExtended.Dummy.outfitShapeState
                })
            val paddingTopIndicator: Dp by DelegateNullFallBack.Ref(
                paddingTopIndicator,
                fallBackValue = { 6.dp })
            val sizeIndicator: Dp by DelegateNullFallBack.Ref(
                sizeIndicator,
                fallBackValue = { 6.dp })
            val spacingIndicator: Dp by DelegateNullFallBack.Ref(
                spacingIndicator,
                fallBackValue = { 6.dp })

            companion object {

                @Composable
                fun Style.copy(scope: @Composable StyleBuilder.() -> Unit) =
                    StyleBuilder(this).also {
                        it.scope()
                    }.get()

                @Composable
                fun Simple.Style.copyToIndicatorStyle(scope: @Composable StyleBuilder.() -> Unit) =
                    StyleBuilder(Style(this)).also { it.scope() }.get()

            }

            constructor(style: Style) : this(
                stylePager = style,
                outfitShapeIndicator = style.outfitShapeIndicator,
                sizeIndicator = style.sizeIndicator,
                spacingIndicator = style.spacingIndicator,
                paddingTopIndicator = style.paddingTopIndicator,
            )
        }

        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: Style,
            itemSelected: Int = 0,
            items: List<@Composable () -> Unit>,
            onItemChange: ((index: Int) -> Unit)? = null
        ) {
            val pagerState = rememberPagerState {
                items.size
            }
            Column(
                modifier = modifier
            ) {
                Simple(
                    modifier = modifier,
                    pagerState = pagerState,
                    style = style,
                    itemSelected = itemSelected,
                    items = items,
                    onItemChange = onItemChange
                )
                style.outfitShapeIndicator.let {
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        pageCount = items.size,
                        modifier = Modifier
                            .padding(top = style.paddingTopIndicator)
                            .align(Alignment.CenterHorizontally),
                        indicatorWidth = style.sizeIndicator,
                        indicatorHeight = style.sizeIndicator,
                        spacing = style.spacingIndicator,
                        activeColor = it.resolveColor(OutfitState.BiStable.Selector.Active)
                            ?: LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                        inactiveColor = it.resolveColor(OutfitState.BiStable.Selector.Inactive)
                            ?: LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                                .copy(
                                    ContentAlpha.disabled
                                ),
                        indicatorShape = it.getShape() ?: CircleShape
                    )
                }
            }
        }
    }

    object WithTabRow {

        class StyleBuilder internal constructor(style: Style) : Simple.StyleBuilder(style) {
            var outfitText = style.outfitText
            var sizeIndicator = style.sizeIndicator
            var colorIndicator = style.colorIndicator

            override fun get() = Style(
                stylePager = super.get(),
                outfitText = outfitText,
                sizeIndicator = sizeIndicator,
                colorIndicator = colorIndicator,
            )
        }

        class Style(
            stylePager: Simple.Style? = null,
            outfitText: OutfitTextStateColor? = null,
            sizeIndicator: DpSize? = null,
            colorIndicator: OutfitStateBiStable<Color>? = null
        ) : Simple.Style(stylePager) {

            val outfitText: OutfitTextStateColor by DelegateNullFallBack.Ref(
                outfitText,
                fallBackValue = {
                    OutfitTextStateColor(
                        outfitState = OutfitStateBiStable(
                            active = ThemeColorsExtended.Dummy.pink,
                            inactive = ThemeColorsExtended.Dummy.blue,
                        )
                    )
                })
            val sizeIndicator: DpSize by DelegateNullFallBack.Ref(
                sizeIndicator,
                fallBackValue = {
                    DpSize(height = 3.5.dp, width = 16.dp)
                })
            val colorIndicator: OutfitStateBiStable<Color> by DelegateNullFallBack.Ref(
                colorIndicator,
                fallBackValue = {
                    OutfitStateBiStable(
                        active = ThemeColorsExtended.Dummy.pink,
                        inactive = ThemeColorsExtended.Dummy.green,
                    )
                })

            companion object {

                internal val INDICATOR_PADDING_VERTICAL = 1.0.dp

                @Composable
                fun Style.copy(scope: @Composable StyleBuilder.() -> Unit) =
                    StyleBuilder(this).also {
                        it.scope()
                    }.get()

                @Composable
                fun Simple.Style.copyToTabRowStyle(scope: @Composable StyleBuilder.() -> Unit) =
                    StyleBuilder(Style(this)).also { it.scope() }.get()

            }

            constructor(style: Style) : this(
                stylePager = style,
                outfitText = style.outfitText,
                sizeIndicator = style.sizeIndicator,
                colorIndicator = style.colorIndicator,
            )
        }

        class Tab(val title: String)

        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: Style,
            itemSelected: Int = 0,
            items: ListEntry<Tab, @Composable () -> Unit>,
            onItemChange: ((index: Int) -> Unit)? = null
        ) {
            val coroutineScope = rememberCoroutineScope()
            val pagerState = rememberPagerState {
                items.size
            }
            Column(
                modifier = modifier
            ) {
                TabRow(modifier = Modifier
                    .fillMaxWidth(),
                    selectedTabIndex = itemSelected,
                    divider = {
                        style.sizeIndicator.takeIf { it.height > 0.dp }?.let {
                            TabRowDefaults.Divider(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.BottomCenter),
                                color = style.colorIndicator.inactive,
                                thickness = it.height + INDICATOR_PADDING_VERTICAL * 2
                            )
                        }
                    },
                    indicator = { tabPositions ->
                        style.sizeIndicator.takeIf { it.height > 0.dp }?.let {
                            Box(
                                Modifier
                                    .padding(vertical = INDICATOR_PADDING_VERTICAL)
                                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                                    .padding(horizontal = it.width)
                                    .fillMaxWidth()
                                    .height(it.height)
                                    .background(style.colorIndicator.active, RoundedCornerShape(50))
                            )
                        }
                    }
                ) {
                    items.forEachIndexed { index, entry ->
                        Tab(
                            modifier = Modifier.background(MaterialTheme.colors.background),
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text.StateColor(
                                    text = entry.key.title,
                                    style = style.outfitText,
                                    selector = if (pagerState.currentPage == index) {
                                        OutfitState.BiStable.Selector.Active
                                    } else {
                                        OutfitState.BiStable.Selector.Inactive
                                    }
                                )
                            })
                    }
                }
                Simple(
                    modifier = modifier,
                    pagerState = pagerState,
                    style = style,
                    itemSelected = itemSelected,
                    items = items.values,
                    onItemChange = onItemChange
                )
            }


        }
    }
}