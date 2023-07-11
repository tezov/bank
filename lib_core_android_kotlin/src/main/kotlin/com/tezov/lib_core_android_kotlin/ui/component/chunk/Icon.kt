/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.chunk

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tezov.lib_core_android_kotlin.ui.modifier.thenOnNotNull
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitFrameStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.background
import com.tezov.lib_core_android_kotlin.ui.theme.theme.ThemeColorsExtended
import com.tezov.lib_core_android_kotlin.ui.type.primaire.DpSize
import com.tezov.lib_core_android_kotlin.ui.type.primaire.size
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack
import androidx.compose.ui.graphics.Color as ColorImport

object Icon {

    object Clickable {

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            radiusRipple: Dp? = null,
            colorRipple: ColorImport? = null,
            onClick: () -> Unit,
            content: @Composable () -> Unit
        ) {
            Box(
                modifier = modifier
                    .clickable(
                        onClick = onClick,
                        enabled = enabled,
                        role = Role.Button,
                        interactionSource = interactionSource,
                        indication = rememberRipple(
                            bounded = false,
                            radius = radiusRipple ?: 24.dp,
                            color = colorRipple ?: ColorImport.Black
                        )
                    ),
            ) {
                content()
            }
        }
    }

    object Simple {

        class StyleBuilder internal constructor(style: Style) {
            var size = style.size
            var tint = style.tint

            internal fun get() = Style(
                size = size,
                tint = tint,
            )
        }

        class Style(
            val size: DpSize? = null,
            tint: ColorImport? = null,
        ) {

            val tint: ColorImport by DelegateNullFallBack.Ref(tint, fallBackValue = {
                ThemeColorsExtended.Dummy.pink
            })

            companion object {

                @Composable
                fun Style.copy(builder: @Composable StyleBuilder.() -> Unit = {}) =
                    StyleBuilder(this).also {
                        it.builder()
                    }.get()

            }

            constructor(style: Style) : this(
                size = style.size,
                tint = style.tint,
            )
        }

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: Style = Style(),
            painter: Painter,
            description: String? = null,
        ) {
            Icon(
                modifier = modifier
                    .thenOnNotNull(style.size) {
                        if (it.width != 0.dp && it.height == 0.dp) {
                            width(it.width).aspectRatio(1.0f)
                        } else if (it.width == 0.dp && it.height != 0.dp) {
                            height(it.width).aspectRatio(1.0f)
                        } else {
                            size(it)
                        }
                    },
                painter = painter,
                tint = style.tint,
                contentDescription = description,
            )
        }

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: Style = Style(),
            resourceId: Int,
            description: String? = null,
        ) {
            invoke(
                modifier,
                style,
                painterResource(id = resourceId),
                description
            )
        }

    }

    object StateColor {

        class StyleBuilder internal constructor(style: Style) {
            var size = style.size
            var tint = style.tint
            var outfitFrame = style.outfitFrame

            internal fun get() = Style(
                size = size,
                tint = tint,
                outfitFrame = outfitFrame,
            )
        }

        class Style(
            val size: DpSize? = null,
            tint: ColorImport? = null,
            val outfitFrame: OutfitFrameStateColor? = null,
        ) {

            val tint: ColorImport by DelegateNullFallBack.Ref(tint, fallBackValue = {
                ThemeColorsExtended.Dummy.pink
            })

            companion object {

                @Composable
                fun Style.copy(builder: @Composable StyleBuilder.() -> Unit = {}) =
                    StyleBuilder(this).also {
                        it.builder()
                    }.get()

            }

            constructor(style: Style) : this(
                size = style.size,
                tint = style.tint,
                outfitFrame = style.outfitFrame,
            )
        }

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: Style = Style(),
            painter: Painter,
            description: String? = null,
            selector: Any? = null
        ) {
            Icon(
                modifier = modifier
                    .thenOnNotNull(style.size) {
                        if (it.width != 0.dp && it.height == 0.dp) {
                            width(it.width).aspectRatio(1.0f)
                        } else if (it.width == 0.dp && it.height != 0.dp) {
                            height(it.width).aspectRatio(1.0f)
                        } else {
                            size(it)
                        }
                    }
                    .thenOnNotNull(style.outfitFrame) {
                        background(it, selector)
                    }
                    .thenOnNotNull(style.size?.padding) {
                        padding(it)
                    },
                painter = painter,
                tint = style.tint,
                contentDescription = description,
            )
        }

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: Style = Style(),
            resourceId: Int,
            description: String? = null,
            selector: Any? = null
        ) {
            invoke(
                modifier,
                style,
                painterResource(id = resourceId),
                description,
                selector
            )
        }
    }
}