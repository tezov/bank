/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.chunk

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import com.tezov.lib_core_android_kotlin.ui.theme.style.*
import com.tezov.lib_core_android_kotlin.ui.theme.theme.ThemeColorsExtended
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack
import androidx.compose.ui.graphics.Color as ColorImport

object Button {

    object StateColor {

        class StyleBuilder internal constructor(
            style: Style
        ) {
            var outfitFrame = style.outfitFrame
            var outfitText = style.outfitText
            var elevation = style.elevation

            internal fun get() = Style(
                outfitFrame = outfitFrame,
                outfitText = outfitText,
                elevation = elevation,
            )
        }

        class Style(
            outfitFrame: OutfitFrameStateColor? = null,
            outfitText: OutfitTextStateColor? = null,
            val elevation: Dp? = null,
        ) {

            val outfitFrame: OutfitFrameStateColor by DelegateNullFallBack.Ref(
                outfitFrame,
                fallBackValue = {
                    ThemeColorsExtended.Dummy.outfitFrameState

                })
            val outfitText: OutfitTextStateColor by DelegateNullFallBack.Ref(
                outfitText,
                fallBackValue = {
                    OutfitTextStateColor()
                })

            companion object {

                @Composable
                fun Style.copy(
                    builder: @Composable StyleBuilder.() -> Unit = {}
                ) = StyleBuilder(this).also {
                    it.builder()
                }.get()

            }

            constructor(style: Style) : this(
                outfitFrame = style.outfitFrame,
                outfitText = style.outfitText,
                elevation = style.elevation,
            )
        }

        @Composable
        operator fun invoke(
            text: AnnotatedString,
            modifierButton: Modifier = Modifier,
            modifierText: Modifier = Modifier,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            style: Style = Style(),
            contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
            enabled: Boolean = true,
            selector: Any? = null,
            onClick: () -> Unit = {},
        ) {
            Button(
                modifier = modifierButton,
                interactionSource = interactionSource,
                elevation = style.elevation?.let { ButtonDefaults.elevation(it, it, it, it, it) },
                shape = style.outfitFrame.getShape() ?: MaterialTheme.shapes.small,
                border = style.outfitFrame.resolveBorder(selector),
                colors = style.outfitFrame.resolveColorShape(selector)?.let {
                    ButtonDefaults.buttonColors(
                        backgroundColor = it,
                        disabledBackgroundColor = it,
                    )
                } ?: ButtonDefaults.buttonColors(
                    backgroundColor = ColorImport.Transparent,
                    disabledBackgroundColor = ColorImport.Transparent,
                ),
                contentPadding = contentPadding,
                enabled = enabled,
                onClick = onClick,
            ) {
                Text.StateColor(
                    modifier = modifierText,
                    text = text,
                    style = style.outfitText,
                    selector = selector
                )
            }
        }

        @Composable
        operator fun invoke(
            text: String,
            modifierButton: Modifier = Modifier,
            modifierText: Modifier = Modifier,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            style: Style = Style(),
            contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
            enabled: Boolean = true,
            selector: Any? = null,
            onClick: () -> Unit = {},
        ) {
            invoke(
                AnnotatedString(text),
                modifierButton,
                modifierText,
                interactionSource,
                style,
                contentPadding,
                enabled,
                selector,
                onClick
            )
        }

        @Composable
        operator fun invoke(
            text: Int,
            modifierButton: Modifier = Modifier,
            modifierText: Modifier = Modifier,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            style: Style = Style(),
            contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
            enabled: Boolean = true,
            selector: Any? = null,
            onClick: () -> Unit = {},
        ) {
            invoke(
                stringResource(id = text),
                modifierButton,
                modifierText,
                interactionSource,
                style,
                contentPadding,
                enabled,
                selector,
                onClick
            )
        }


//        *** selector dual shortcut

        @Composable
        operator fun invoke(
            text: AnnotatedString,
            modifierButton: Modifier = Modifier,
            modifierText: Modifier = Modifier,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            style: Style = Style(),
            contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
            enabled: Boolean = true,
            onClick: () -> Unit = {},
        ) {
            invoke(
                text,
                modifierButton,
                modifierText,
                interactionSource,
                style,
                contentPadding,
                enabled,
                if (enabled) OutfitState.BiStable.Selector.Active else OutfitState.BiStable.Selector.Inactive,
                onClick
            )
        }

        @Composable
        operator fun invoke(
            text: String,
            modifierButton: Modifier = Modifier,
            modifierText: Modifier = Modifier,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            style: Style = Style(),
            contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
            enabled: Boolean = true,
            onClick: () -> Unit = {},
        ) {
            invoke(
                AnnotatedString(text),
                modifierButton,
                modifierText,
                interactionSource,
                style,
                contentPadding,
                enabled,
                onClick
            )
        }

        @Composable
        operator fun invoke(
            text: Int,
            modifierButton: Modifier = Modifier,
            modifierText: Modifier = Modifier,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            style: Style = Style(),
            contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
            enabled: Boolean = true,
            onClick: () -> Unit = {},
        ) {
            invoke(
                stringResource(id = text),
                modifierButton,
                modifierText,
                interactionSource,
                style,
                contentPadding,
                enabled,
                onClick
            )
        }

    }

}