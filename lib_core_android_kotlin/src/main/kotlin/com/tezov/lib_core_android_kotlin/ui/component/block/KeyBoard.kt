/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.block

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.sp
import com.tezov.lib_core_android_kotlin.ui.type.primaire.QuadDirection
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitBorderStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitFrameStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitState
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.theme.*
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack
import com.tezov.lib_core_kotlin.extension.ExtensionBoolean.toInt
import kotlin.properties.Delegates

object KeyBoard {

    object GridCubeDigitsTwoRowShuffled {

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: GridCube.Style = GridCube.Style(),
            enabled: Boolean = true,
            selector: Any? = null,
            onclick: (value: String) -> Unit = {}
        ) {
            val keyBoardDigits = remember {
                val digits = List(10) {
                    GridCube.Common.CubeChar(it.toString()[0])
                }.shuffled()
                GridCube.Common.CubesChar(2, digits) {
                    if (enabled) onclick(char)
                }
            }
            GridCube(modifier = modifier, style = style, keyBoardDigits, selector = selector)
        }

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            style: GridCube.Style = GridCube.Style(),
            enabled: Boolean = true,
            onclick: (value: String) -> Unit = {}
        ) {
            invoke(
                modifier = modifier,
                style = style,
                enabled = enabled,
                selector = if (enabled) OutfitState.BiStable.Selector.Active else OutfitState.BiStable.Selector.Inactive,
                onclick = onclick,
            )
        }

    }

    object GridCube {

        class StyleBuilder internal constructor(style: Style) {
            var outfitText = style.outfitText
            var outfitFrameOuter = style.outfitFrameOuter
            var outfitBorderInner = style.outfitBorderInner

            internal fun get() = Style(
                outfitText = outfitText,
                outfitFrameOuter = outfitFrameOuter,
                outfitBorderInner = outfitBorderInner,
            )
        }

        class Style(
            outfitText: OutfitTextStateColor? = null,
            outfitFrameOuter: OutfitFrameStateColor? = null,
            outfitBorderInner: OutfitBorderStateColor? = null,
            val drawOutsideLine: QuadDirection<Boolean, Boolean, Boolean, Boolean> = QuadDirection(
                top = false,
                start = false,
                bottom = false,
                end = false
            )
        ) {
            val outfitText: OutfitTextStateColor by DelegateNullFallBack.Ref(
                outfitText,
                fallBackValue = { OutfitTextStateColor() }
            )
            val outfitFrameOuter: OutfitFrameStateColor by DelegateNullFallBack.Ref(
                outfitFrameOuter,
                fallBackValue = {
                    ThemeColorsExtended.Dummy.outfitFrameState
                })
            val outfitBorderInner: OutfitBorderStateColor by DelegateNullFallBack.Ref(
                outfitBorderInner,
                fallBackValue = {
                    ThemeColorsExtended.Dummy.outfitBorderState
                })

            companion object {

                @Composable
                fun Style.copy(builder: @Composable StyleBuilder.() -> Unit) =
                    StyleBuilder(this).also {
                        it.builder()
                    }.get()

            }

            constructor(style: Style) : this(
                outfitText = style.outfitText,
                outfitFrameOuter = style.outfitFrameOuter,
                outfitBorderInner = style.outfitBorderInner,
            )
        }

        interface Cube
        interface Cubes<C : Cube> {

            val rowCount: Int

            val columnCount: Int get() = size / rowCount

            val values: List<C>

            val size get() = values.size

            fun forEach(action: (C) -> Unit) = values.forEach(action)

            fun forEachIndexed(action: (Int, C) -> Unit) = values.forEachIndexed(action)

            fun onClicked(index: Int) = runCatching { values[index] }.getOrNull()?.onClicked()

            fun C.onClicked()

            @Composable
            fun prepareDraw(style: Style, selector: Any?)

            fun beforeDraw(cubeSize: Size, style: Style, selector: Any?)

            fun onDraw(cube: C, scope: DrawScope, style: Style, selector: Any?)
        }

        @Composable
        operator fun <P : Cubes<C>, C : Cube> invoke(
            modifier: Modifier = Modifier,
            style: Style,
            cubes: P,
            selector: Any? = null,
        ) {
            Layout(
                measurePolicy = { _, constraints ->
                    with(constraints) {
                        val cubeSize = maxWidth / cubes.columnCount
                        val maxHeight = cubeSize * cubes.rowCount
                        layout(maxWidth, maxHeight) {}
                    }
                },
                content = {},
                modifier = modifier
                    .onTouched(cubes)
                    .onDraw(style, cubes, selector)
            )
        }

        @Composable
        private fun <P : Cubes<C>, C : Cube> Modifier.onTouched(cubes: P): Modifier {
            return pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val xPosition = ((offset.x / size.width) * cubes.columnCount).toInt()
                        val yPosition = ((offset.y / size.height) * cubes.rowCount).toInt()
                        val index = cubes.columnCount * yPosition + xPosition
                        cubes.onClicked(index)
                    }
                )
            }
        }

        @Composable
        private fun <P : Cubes<C>, C : Cube> Modifier.onDraw(
            style: Style,
            cubes: P,
            selector: Any? = null
        ): Modifier {
            cubes.prepareDraw(style = style, selector = selector)
            val density = LocalDensity.current
            return drawBehind {
                //background
                style.outfitFrameOuter.resolveColorShape(selector)?.let {
                    drawRect(
                        color = it,
                        topLeft = Offset(0f, 0f),
                        size = Size(size.width, size.height),
                        style = Fill,
                    )
                }
                // border outer
                style.outfitFrameOuter.resolveBorder(selector)?.let { border ->
                    val color =
                        style.outfitFrameOuter.resolveColorBorder(selector) ?: Color.Transparent
                    style.outfitFrameOuter.outfitShape.size?.let { cornerSize ->
                        val size = Size(size.width, size.height)
                        val path = Path().apply {
                            addRoundRect(
                                RoundRect(
                                    rect = Rect(
                                        offset = Offset.Zero,
                                        size = size,
                                    ),
                                    topLeft = cornerSize.topStart?.let {
                                        val px = it.toPx(size, density)
                                        CornerRadius(px, px)
                                    } ?: CornerRadius.Zero,
                                    topRight = cornerSize.topEnd?.let {
                                        val px = it.toPx(size, density)
                                        CornerRadius(px, px)
                                    } ?: CornerRadius.Zero,
                                    bottomLeft = cornerSize.bottomStart?.let {
                                        val px = it.toPx(size, density)
                                        CornerRadius(px, px)
                                    } ?: CornerRadius.Zero,
                                    bottomRight = cornerSize.bottomEnd?.let {
                                        val px = it.toPx(size, density)
                                        CornerRadius(px, px)
                                    } ?: CornerRadius.Zero
                                )
                            )
                        }
                        drawPath(
                            path = path,
                            color = color,
                            style = Stroke(width = border.width.toPx())
                        )
                    } ?: run {
                        drawRect(
                            brush = border.brush,
                            topLeft = Offset(0f, 0f),
                            size = Size(size.width, size.height),
                            style = Stroke(width = border.width.toPx()),
                        )
                    }
                }
                //line
                val quad = style.drawOutsideLine
                style.outfitBorderInner.resolve(selector)?.let {
                    val strokeSize = it.width.toPx()
//                    //horizontal line
                    val yAvailable =
                        (size.height - strokeSize * quad.top.toInt() - strokeSize * quad.bottom.toInt())
                    val ySpacing = yAvailable / cubes.rowCount
                    for (i in (!quad.top).toInt() until (cubes.rowCount + quad.bottom.toInt())) {
                        val yTranslate = (ySpacing * i) + strokeSize
                        drawLine(
                            start = Offset(x = 0f, y = yTranslate),
                            end = Offset(x = size.width, y = yTranslate),
                            brush = it.brush,
                            strokeWidth = strokeSize
                        )
                    }
                    //vertical line
                    val xAvailable =
                        (size.width - strokeSize * quad.start.toInt() - strokeSize * quad.end.toInt())
                    val xSpacing = xAvailable / cubes.columnCount
                    for (i in (!quad.start).toInt() until (cubes.columnCount + quad.end.toInt())) {
                        val xTranslate = (xSpacing * i) + strokeSize
                        drawLine(
                            start = Offset(x = xTranslate, y = 0f),
                            end = Offset(x = xTranslate, y = size.height),
                            brush = it.brush,
                            strokeWidth = it.width.toPx()
                        )

                    }
                }
                val overflow = cubes.columnCount * cubes.rowCount
                //cubes
                val cubeSize = size.width / cubes.columnCount
                val drawContextSizeSave = drawContext.size
                drawContext.size = Size(cubeSize, cubeSize)
                cubes.beforeDraw(cubeSize = drawContext.size, style = style, selector = selector)
                cubes.forEachIndexed { index, cube ->
                    if (index >= overflow) {
                        return@forEachIndexed
                    }
                    val x = (index % cubes.columnCount) * cubeSize
                    val y = (index / cubes.columnCount) * cubeSize
                    translate(x, y) {
                        clipRect(0f, 0f, cubeSize, cubeSize) {
                            cubes.onDraw(cube = cube, scope = this, style, selector = selector)
                        }
                    }
                }
                drawContext.size = drawContextSizeSave
            }
        }

        object Common {

            class CubeChar(char: Char) : Cube {
                internal val char = char.toString()
            }

            @OptIn(ExperimentalTextApi::class)
            class CubesChar(
                override val rowCount: Int,
                override val values: List<CubeChar>,
                val onclick: CubeChar.() -> Unit
            ) : Cubes<CubeChar> {

                private lateinit var textMeasurer: TextMeasurer
                private lateinit var textStyle: TextStyle
                private var textOffset by Delegates.notNull<Offset>()

                override fun CubeChar.onClicked() = onclick()

                @Composable
                override fun prepareDraw(style: Style, selector: Any?) {
                    textMeasurer = rememberTextMeasurer()
                }

                override fun beforeDraw(cubeSize: Size, style: Style, selector: Any?) {
                    textStyle = style.outfitText.resolve(selector).copy(
                        fontSize = (cubeSize.width * 0.25).toFloat().sp,
                    )
                    textOffset = Offset((cubeSize.width / 3.5).toFloat(), 0f)
                }

                override fun onDraw(
                    cube: CubeChar,
                    scope: DrawScope,
                    style: Style,
                    selector: Any?
                ) {
                    val measuredText = textMeasurer.measure(
                        text = AnnotatedString(cube.char),
                        style = textStyle
                    )
                    scope.drawText(
                        textLayoutResult = measuredText,
                        topLeft = textOffset,
                    )
                }

            }

        }

    }

}