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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import com.tezov.lib_core_android_kotlin.ui.modifier.then
import com.tezov.lib_core_android_kotlin.ui.modifier.thenOnNotNull
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.padding
import com.tezov.lib_core_android_kotlin.ui.theme.theme.ThemeColorsExtended
import com.tezov.lib_core_android_kotlin.ui.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_core_android_kotlin.ui.theme.theme.shapesExtended

object Text {

    object Clickable {

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            contentPadding: PaddingValues? = null,
            colorRipple: Color? = null,
            onClick: () -> Unit,
            content: @Composable () -> Unit
        ) {
            Box(
                modifier = modifier
                    .thenOnNotNull(MaterialTheme.shapesExtended.element.small.getShape()) {
                        clip(it)
                    }
                    .clickable(
                        onClick = onClick,
                        enabled = enabled,
                        role = Role.Button,
                        interactionSource = interactionSource,
                        indication = rememberRipple(
                            bounded = true,
                            color = colorRipple ?: Color.Black
                        )
                    )
                    .then(contentPadding,
                        onNotNull = { padding(it) },
                        onNull = { padding(MaterialTheme.dimensionsPaddingExtended.chunk.small) }
                    ),
            ) {
                content()
            }
        }

    }

    object StateColor {

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: OutfitTextStateColor? = null,
            overflow: TextOverflow = TextOverflow.Clip,
            softWrap: Boolean = true,
            maxLines: Int = Int.MAX_VALUE,
            onTextLayout: (TextLayoutResult) -> Unit = {},
            text: AnnotatedString,
            selector: Any? = null
        ) {
            Text(
                modifier = modifier,
                text = text,
                style = style?.resolve(selector) ?: kotlin.run {
                    ThemeColorsExtended.Dummy.textStyle
                },
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                onTextLayout = onTextLayout,
            )
        }

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: OutfitTextStateColor? = null,
            overflow: TextOverflow = TextOverflow.Clip,
            softWrap: Boolean = true,
            maxLines: Int = Int.MAX_VALUE,
            onTextLayout: (TextLayoutResult) -> Unit = {},
            text: String,
            selector: Any? = null
        ) {
            invoke(
                modifier,
                style,
                overflow,
                softWrap,
                maxLines,
                onTextLayout,
                AnnotatedString(text),
                selector,
            )
        }

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: OutfitTextStateColor? = null,
            overflow: TextOverflow = TextOverflow.Clip,
            softWrap: Boolean = true,
            maxLines: Int = Int.MAX_VALUE,
            onTextLayout: (TextLayoutResult) -> Unit = {},
            text: Int,
            selector: Any? = null
        ) {
            invoke(
                modifier,
                style,
                overflow,
                softWrap,
                maxLines,
                onTextLayout,
                stringResource(id = text),
                selector,
            )
        }

    }

}