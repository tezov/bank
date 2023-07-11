/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.chunk

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tezov.lib_core_android_kotlin.ui.modifier.thenOnNotNull
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitFrameStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.background
import com.tezov.lib_core_android_kotlin.ui.type.primaire.DpSize
import com.tezov.lib_core_android_kotlin.ui.type.primaire.size

object Image {

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
            val tint: Color? = null,
            val contentScale: ContentScale = ContentScale.Fit,
        ) {

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
            Image(
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
                colorFilter = style.tint?.let { ColorFilter.tint(it) },
                contentDescription = description,
                contentScale = style.contentScale
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
            var contentScale = style.contentScale

            internal fun get() = Style(
                size = size,
                tint = tint,
                outfitFrame = outfitFrame,
                contentScale = contentScale,
            )
        }

        class Style(
            val size: DpSize? = null,
            val tint: Color? = null,
            val outfitFrame: OutfitFrameStateColor? = null,
            val contentScale: ContentScale = ContentScale.Fit
        ) {

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
                contentScale = style.contentScale,
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

            Image(
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
                    },
                painter = painter,
                colorFilter = style.tint?.let { ColorFilter.tint(it) },
                contentDescription = description,
                contentScale = style.contentScale
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