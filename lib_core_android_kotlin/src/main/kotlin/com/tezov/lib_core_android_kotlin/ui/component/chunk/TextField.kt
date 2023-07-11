/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.chunk

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import com.tezov.lib_core_android_kotlin.ui.misc.FocusDispatcher
import com.tezov.lib_core_android_kotlin.ui.modifier.thenOnNotNull
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitState
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.theme.ThemeColorsExtended

object TextField {

    //TODO cursor set position doesn't work...
    class State(initial: String, cursorPosition: Int) {

        constructor(initial: String, cursorAtEnd: Boolean = true) : this(
            initial,
            if (cursorAtEnd) initial.length else 0
        )

        var focusId: FocusDispatcher.FocusId? = null

        internal val currentFieldValueState: MutableState<TextFieldValue> = mutableStateOf(
            TextFieldValue(
                text = initial,
                selection = TextRange(cursorPosition),
            )
        )

        internal var nextFieldValue: TextFieldValue? = null

        var current: String
            get() = currentFieldValueState.value.text
            set(value) {
                currentFieldValueState.value = currentFieldValueState.value.copy(text = value)
            }

        val next get() = nextFieldValue?.text ?: current

    }

    internal abstract class TextFieldColors : androidx.compose.material.TextFieldColors {

        private val dummy = mutableStateOf(ThemeColorsExtended.Dummy.green)
        var selector: Any? = null

        @Composable
        final override fun labelColor(
            enabled: Boolean,
            error: Boolean,
            interactionSource: InteractionSource
        ) = dummy

        @Composable
        final override fun textColor(enabled: Boolean) = dummy

        @Composable
        final override fun placeholderColor(enabled: Boolean) = dummy

        @Composable
        final override fun leadingIconColor(enabled: Boolean, isError: Boolean) = dummy

        @Composable
        final override fun trailingIconColor(enabled: Boolean, isError: Boolean) = dummy

    }

    object StateColor {

        class StyleBuilder internal constructor(style: Style) {
            var outfitText = style.outfitText
            var outfitLabel = style.outfitLabel
            var outfitPlaceholder = style.outfitPlaceholder
            var styleIconStart = style.styleIconStart
            var styleIconEnd = style.styleIconEnd
            var colorBackground = style.colorBackground
            var colorCursor = style.colorCursor

            internal fun get() = Style(
                outfitText = outfitText,
                outfitLabel = outfitLabel,
                outfitPlaceholder = outfitPlaceholder,
                styleIconStart = styleIconStart,
                styleIconEnd = styleIconEnd,
                colorBackground = colorBackground,
                colorCursor = colorCursor,
            )
        }

        class Style(
            val outfitText: OutfitTextStateColor? = null,
            val outfitLabel: OutfitTextStateColor? = null,
            val outfitPlaceholder: OutfitTextStateColor? = null,
            val styleIconStart: Icon.StateColor.Style? = null,
            val styleIconEnd: Icon.StateColor.Style? = null,
            val colorBackground: OutfitState.Style<Color>? = null,
            val colorCursor: OutfitState.Style<Color>? = null,
            val colorLine: OutfitState.Style<Color>? = null,
        ) {

            internal val colors: TextFieldColors = object : TextFieldColors() {
                private val ANIMATION_DURATION = 150

                @Composable
                override fun backgroundColor(enabled: Boolean) =
                    rememberUpdatedState(
                        colorBackground?.resolve(
                            selector ?: kotlin.run {
                                if (enabled) OutfitState.BiStable.Selector.Active
                                else OutfitState.BiStable.Selector.Inactive
                            }
                        ) ?: Color.Transparent
                    )

                @Composable
                override fun cursorColor(isError: Boolean) =
                    rememberUpdatedState(
                        colorCursor?.resolve(
                            selector ?: kotlin.run {
                                if (isError) OutfitState.Input.Selector.Error
                                else OutfitState.Input.Selector.Focused
                            }
                        ) ?: ThemeColorsExtended.Dummy.pink
                    )

                @Composable
                override fun indicatorColor(
                    enabled: Boolean,
                    isError: Boolean,
                    interactionSource: InteractionSource
                ): androidx.compose.runtime.State<Color> {
                    val targetValue = colorLine?.resolve(
                        selector ?: kotlin.run {
                            val focused = interactionSource.collectIsFocusedAsState()
                            when {
                                isError -> OutfitState.Input.Selector.Error
                                !enabled -> OutfitState.Input.Selector.Inactive
                                focused.value -> OutfitState.Input.Selector.Focused
                                else -> OutfitState.Input.Selector.Unfocused
                            }
                        }
                    ) ?: ThemeColorsExtended.Dummy.pink
                    return if (enabled) {
                        animateColorAsState(targetValue, tween(durationMillis = ANIMATION_DURATION))
                    } else {
                        rememberUpdatedState(targetValue)
                    }
                }
            }

            companion object {

                @Composable
                fun Style.copy(builder: @Composable StyleBuilder.() -> Unit = {}) =
                    StyleBuilder(this).also {
                        it.builder()
                    }.get()

            }

            constructor(style: Style) : this(
                outfitText = style.outfitText,
                outfitLabel = style.outfitLabel,
                outfitPlaceholder = style.outfitPlaceholder,
                styleIconStart = style.styleIconStart,
                styleIconEnd = style.styleIconEnd,
                colorBackground = style.colorBackground,
                colorCursor = style.colorCursor,
            )
        }

        @Composable
        operator fun invoke(
            state: State,
            label: String? = null,
            placeholder: String? = null,
            painterIconStart: Painter? = null,
            descriptionIconStart: String? = null,
            onClickIconStart: (() -> Unit)? = null,
            painterIconEnd: Painter? = null,
            descriptionIconEnd: String? = null,
            onClickIconEnd: (() -> Unit)? = null,
            modifier: Modifier = Modifier,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            visualTransformation: VisualTransformation = VisualTransformation.None,
            style: Style = Style(),
            keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
            keyboardActions: KeyboardActions = KeyboardActions(),
            singleLine: Boolean = false,
            maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
            minLines: Int = 1,
            readOnly: Boolean = false,
            enabled: Boolean = true,
            isError: Boolean = false,
            selector: Any? = null,
            acceptChange: (State) -> Boolean = { true },
            onChanged: (State) -> Unit = { true },
        ) {
            style.colors.selector = selector
            TextField(
                modifier = modifier
                    .thenOnNotNull(state.focusId) {
                        focusRequester(it.value)
                            .onFocusChanged { state ->
                                if (state.isFocused) {
                                    it.onFocus()
                                }
                            }
                    },
                interactionSource = interactionSource,
                visualTransformation = visualTransformation,
                value = state.currentFieldValueState.value,
                label = label?.let {
                    {
                        Text.StateColor(
                            text = it,
                            style = style.outfitLabel,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            selector = selector
                        )
                    }
                },
                placeholder = placeholder?.let {
                    {
                        Text.StateColor(
                            text = it,
                            style = style.outfitPlaceholder,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            selector = selector
                        )
                    }
                },
                onValueChange = {
                    state.nextFieldValue = it
                    if (acceptChange(state).also { state.nextFieldValue = null }) {
                        state.currentFieldValueState.value = it
                        onChanged(state)
                    }
                },
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                colors = style.colors,
                textStyle = style.outfitText?.resolve(selector) ?: kotlin.run {
                    ThemeColorsExtended.Dummy.textStyle
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                leadingIcon = {
                    painterIconStart?.let {
                        val styleIcon = style.styleIconStart ?: Icon.StateColor.Style()
                        Icon.Clickable(
                            enabled = onClickIconStart != null,
                            radiusRipple = styleIcon.size?.radiusMin,
                            colorRipple = styleIcon.tint,
                            onClick = onClickIconStart ?: {}
                        ) {
                            Icon.StateColor(
                                style = styleIcon,
                                painter = it,
                                description = descriptionIconStart,
                                selector = selector
                            )
                        }
                    }
                },
                trailingIcon = {
                    painterIconEnd?.let {
                        val styleIcon = style.styleIconEnd ?: Icon.StateColor.Style()
                        Icon.Clickable(
                            enabled = onClickIconEnd != null,
                            radiusRipple = styleIcon.size?.radiusMin,
                            colorRipple = styleIcon.tint,
                            onClick = onClickIconEnd ?: {}
                        ) {
                            Icon.StateColor(
                                style = styleIcon,
                                painter = it,
                                description = descriptionIconEnd,
                                selector = selector
                            )
                        }
                    }
                },
                readOnly = readOnly,
                enabled = enabled,
                isError = isError
            )
        }

    }

}