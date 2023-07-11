/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tezov.lib_core_android_kotlin.animation.AnimationProgress
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalActivity

object NavigationAnimation {

    class Config private constructor(default: Type) {

        companion object{

            operator fun invoke(default: Type = Type.Fade(), block: Config.() -> Unit = {}) =
                Config(default).also(block)
        }

        sealed class Type {
            object None : Type()

            class Fade(
                val duration_ms: Int = NavigationAnimation.Fade.DURATION_ms
            ) : Type()

            class SlideHorizontal(
                val duration_ms: Int = Slide.Horizontal.DURATION_ms,
                val outDarkAlphaFactor: Float = Slide.Horizontal.OUT_DARK_ALPHA_FACTOR,
                val entrance: Slide.Horizontal.Entrance = Slide.Horizontal.Entrance.FromEnd,
                val effect: Slide.Effect = Slide.Effect.CoverPush,
            ) : Type()

            class SlideVertical(
                val duration_ms: Int = Slide.Vertical.DURATION_ms,
                val outDarkAlphaFactor: Float = Slide.Vertical.OUT_DARK_ALPHA_FACTOR,
                val entrance: Slide.Vertical.Entrance = Slide.Vertical.Entrance.FromBottom,
                val effect: Slide.Effect = Slide.Effect.CoverPush,
            ) : Type()
        }

        class Scope(default: Type) {

            var push: Type = default

            var pop: Type = default

        }

        internal var enter: Scope = Scope(default)
        internal var exit: Scope = Scope(default)

        fun enter(block: Scope.() -> Unit) {
            enter.block()
        }

        fun exit(block: Scope.() -> Unit) {
            exit.block()
        }
    }


    internal object Direction {
        enum class Nav { Push, Pop }
        enum class Content { Enter, Exit }
    }

    internal abstract class ModifierAnimation : Modifier {
        private val modifier = Modifier.composed { animate() }

        @Composable
        abstract fun Modifier.animate(): Modifier

        final override fun all(predicate: (Modifier.Element) -> Boolean) = modifier.all(predicate)

        final override fun any(predicate: (Modifier.Element) -> Boolean) = modifier.any(predicate)

        final override fun <R> foldIn(initial: R, operation: (R, Modifier.Element) -> R): R =
            modifier.foldIn(initial, operation)

        final override fun <R> foldOut(initial: R, operation: (Modifier.Element, R) -> R): R =
            modifier.foldOut(initial, operation)
    }

    internal class None : ModifierAnimation() {

        @Composable
        override fun Modifier.animate() = this
    }

    internal object Fade {

        const val DURATION_ms = 250

        fun AnimationProgress.fade(
            config: Config.Type.Fade,
            directionContent: Direction.Content
        ): ModifierAnimation {
            return when (directionContent) {
                Direction.Content.Enter -> In(config, this)
                Direction.Content.Exit -> Out(config, this)
            }
        }

        class In(
            private val config: Config.Type.Fade,
            private val animationProgress: AnimationProgress,
        ) : ModifierAnimation() {

            @Composable
            override fun Modifier.animate(): Modifier {
                val progress = animationProgress.animateFloat(
                    startValue = 0.0f,
                    endValue = 1.0f,
                    animationSpecToEnd = tween(
                        durationMillis = config.duration_ms,
                        easing = LinearEasing
                    )
                )
                return this
                    .alpha(progress.value)
            }
        }

        class Out(
            private val config: Config.Type.Fade,
            private val animationProgress: AnimationProgress,
        ) : ModifierAnimation() {

            @Composable
            override fun Modifier.animate(): Modifier {
                val progress = animationProgress.animateFloat(
                    startValue = 1.0f,
                    endValue = 0.5f,
                    animationSpecToEnd = tween(
                        durationMillis = config.duration_ms,
                        easing = LinearEasing
                    )
                )
                return this
                    .alpha(progress.value)
            }
        }

    }

    object Slide {

        enum class Effect { CoverPush, Cover, Push }

        object Horizontal {

            internal const val DURATION_ms = 200
            internal const val OUT_DARK_ALPHA_FACTOR = 0.75f

            enum class Entrance { FromEnd, FromStart }

            internal fun AnimationProgress.slideHorizontal(
                config: Config.Type.SlideHorizontal,
                directionNav: Direction.Nav,
                directionContent: Direction.Content,
            ): ModifierAnimation {
                return when (directionContent) {
                    Direction.Content.Enter -> when (directionNav) {
                        Direction.Nav.Push -> In(config, this, Direction.Nav.Push)
                        Direction.Nav.Pop -> Out(config, this, Direction.Nav.Pop)
                    }
                    Direction.Content.Exit -> when (directionNav) {
                        Direction.Nav.Push -> Out(config, this, Direction.Nav.Push)
                        Direction.Nav.Pop -> In(config, this, Direction.Nav.Pop)
                    }
                }
            }

            internal class In(
                private val config: Config.Type.SlideHorizontal,
                private val animationProgress: AnimationProgress,
                directionNav: Direction.Nav,
            ) : ModifierAnimation() {

                private val startValue = when (directionNav) {
                    Direction.Nav.Push -> 1.0f
                    Direction.Nav.Pop -> 0.0f
                }
                private val endValue = when (directionNav) {
                    Direction.Nav.Push -> 0.0f
                    Direction.Nav.Pop -> 1.0f
                }

                private val entranceFactor = when (config.entrance) {
                    Entrance.FromEnd -> 1.0f
                    Entrance.FromStart -> -1.0f
                }

                @Composable
                override fun Modifier.animate(): Modifier {
                    val progress = animationProgress.animateFloat(
                        startValue = startValue,
                        endValue = endValue,
                        animationSpecToEnd = tween(
                            durationMillis = config.duration_ms,
                            easing = LinearEasing
                        )
                    )
                    val width = LocalActivity.size.width * entranceFactor
                    return this
                        .offset(x = width * progress.value, y = 0.dp)
                }
            }

            internal class Out(
                private val config: Config.Type.SlideHorizontal,
                private val animationProgress: AnimationProgress,
                directionNav: Direction.Nav,
            ) : ModifierAnimation() {

                private val startValue = when (directionNav) {
                    Direction.Nav.Push -> 0.0f
                    Direction.Nav.Pop -> -1.0f
                }
                private val endValue = when (directionNav) {
                    Direction.Nav.Push -> -1.0f
                    Direction.Nav.Pop -> 0.0f
                }

                private val entranceFactor = when (config.entrance) {
                    Entrance.FromEnd -> 1.0f
                    Entrance.FromStart -> -1.0f
                }

                @Composable
                override fun Modifier.animate(): Modifier {
                    val progress = animationProgress.animateFloat(
                        startValue = startValue,
                        endValue = endValue,
                        animationSpecToEnd = tween(
                            durationMillis = config.duration_ms,
                            easing = LinearEasing
                        )
                    )

                    val width = when (config.effect) {
                        Effect.CoverPush -> {
                            (LocalActivity.size.width * entranceFactor) / 2
                        }
                        Effect.Push -> {
                            (LocalActivity.size.width * entranceFactor)
                        }
                        Effect.Cover -> {
                            0.dp
                        }
                    }
                    return this
                        .offset(x = width * progress.value, y = 0.dp)
                        .drawWithContent {
                            drawContent()
                            drawRect(
                                color = Color.Black,
                                alpha = -progress.value * config.outDarkAlphaFactor,
                                topLeft = Offset(0f, 0f),
                                size = size,
                                style = Fill
                            )
                        }
                }
            }

        }

        object Vertical {

            internal const val DURATION_ms = 300
            internal const val OUT_DARK_ALPHA_FACTOR = 0.60f

            enum class Entrance { FromBottom, FromTop }

            internal fun AnimationProgress.slideVertical(
                config: Config.Type.SlideVertical,
                directionNav: Direction.Nav,
                directionContent: Direction.Content,
            ): ModifierAnimation {
                return when (directionContent) {
                    Direction.Content.Enter -> when (directionNav) {
                        Direction.Nav.Push -> In(config, this, Direction.Nav.Push)
                        Direction.Nav.Pop -> Out(config, this, Direction.Nav.Pop)
                    }
                    Direction.Content.Exit -> when (directionNav) {
                        Direction.Nav.Push -> Out(config, this, Direction.Nav.Push)
                        Direction.Nav.Pop -> In(config, this, Direction.Nav.Pop)
                    }
                }
            }

            internal class In(
                private val config: Config.Type.SlideVertical,
                private val animationProgress: AnimationProgress,
                directionNav: Direction.Nav,
            ) : ModifierAnimation() {

                private val startValue = when (directionNav) {
                    Direction.Nav.Push -> 1.0f
                    Direction.Nav.Pop -> 0.0f
                }
                private val endValue = when (directionNav) {
                    Direction.Nav.Push -> 0.0f
                    Direction.Nav.Pop -> 1.0f
                }

                private val entranceFactor = when (config.entrance) {
                    Entrance.FromBottom -> 1.0f
                    Entrance.FromTop -> -1.0f
                }

                @Composable
                override fun Modifier.animate(): Modifier {
                    val progress = animationProgress.animateFloat(
                        startValue = startValue,
                        endValue = endValue,
                        animationSpecToEnd = tween(
                            durationMillis = config.duration_ms,
                            easing = LinearEasing
                        )
                    )
                    val height = LocalActivity.size.height * entranceFactor
                    return this
                        .offset(x = 0.dp, y = height * progress.value)
                }
            }

            internal class Out(
                private val config: Config.Type.SlideVertical,
                private val animationProgress: AnimationProgress,
                directionNav: Direction.Nav,
            ) : ModifierAnimation() {

                private val startValue = when (directionNav) {
                    Direction.Nav.Push -> 0.0f
                    Direction.Nav.Pop -> -1.0f
                }
                private val endValue = when (directionNav) {
                    Direction.Nav.Push -> -1.0f
                    Direction.Nav.Pop -> 0.0f
                }

                private val entranceFactor = when (config.entrance) {
                    Entrance.FromBottom -> 1.0f
                    Entrance.FromTop -> -1.0f
                }

                @Composable
                override fun Modifier.animate(): Modifier {
                    val progress = animationProgress.animateFloat(
                        startValue = startValue,
                        endValue = endValue,
                        animationSpecToEnd = tween(
                            durationMillis = config.duration_ms,
                            easing = LinearEasing
                        )
                    )
                    val height = when (config.effect) {
                        Effect.CoverPush -> {
                            (LocalActivity.size.height * entranceFactor) / 4
                        }
                        Effect.Push -> {
                            (LocalActivity.size.height * entranceFactor)
                        }
                        Effect.Cover -> {
                            0.dp
                        }
                    }
                    return this
                        .offset(x = 0.dp, y = height * progress.value)
                        .drawWithContent {
                            drawContent()
                            drawRect(
                                color = Color.Black,
                                alpha = -progress.value * config.outDarkAlphaFactor,
                                topLeft = Offset(0f, 0f),
                                size = size,
                                style = Fill
                            )
                        }
                }
            }

        }


    }

}