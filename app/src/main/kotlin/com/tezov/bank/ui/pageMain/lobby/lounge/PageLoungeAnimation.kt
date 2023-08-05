

package com.tezov.bank.ui.pageMain.lobby.lounge

import androidx.compose.animation.core.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeAnimation.Companion.DURATION_LOGO_APPEAR_ms
import com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeAnimation.Companion.DURATION_LOGO_ROTATE_ms
import com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeAnimation.Companion.DURATION_LOGO_TRANSLATE_ms
import com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeAnimation.Companion.DURATION_PAGE_APPEAR_ms
import com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeAnimation.Step
import com.tezov.lib_adr_sdk_core.animation.AnimationCompound
import com.tezov.lib_adr_sdk_core.ui.compositionTree.activity.Activity.Companion.LocalActivity
import com.tezov.lib_adr_sdk_core.ui.theme.theme.dimensionsPaddingExtended

//TODO improve allocate when needed and release when done
class PageLoungeAnimation : AnimationCompound<String, Step>() {

    enum class Step(private val order: Int) {
        Origin(0),
        LogoAppear(1),
        LogoRotate(2),
        Idle(3),
        LogoTranslateAndPageAppear(4),
        Done(5);

        fun isAtLeast(step: Step) = this.order >= step.order

        companion object {
            val Saver = run {
                val keyStep = "keyStep"
                mapSaver(
                    save = { mapOf(keyStep to it.currentState.name) },
                    restore = { MutableTransitionState(valueOf(it[keyStep] as String)) }
                )
            }
        }
    }

    companion object {
        private const val ANIMATION_IDLE = "idle"
        private const val ANIMATION_LOGO = "logo"
        private const val ANIMATION_RATING = "rating"
        private const val ANIMATION_PAGE = "page"

        internal const val DURATION_IDLE_ms = 1000
        internal const val DURATION_LOGO_APPEAR_ms = 250
        internal const val DURATION_LOGO_ROTATE_ms = 150
        internal const val DURATION_LOGO_TRANSLATE_ms = 400
        internal const val DURATION_PAGE_APPEAR_ms = 500

        @Composable
        fun remember(): PageLoungeAnimation {
            return PageLoungeAnimation().apply {
                add(
                    ANIMATION_IDLE,
                    AnimatorIdle<Step>(
                        duration_ms = DURATION_IDLE_ms,
                        transformer = { parentStep -> parentStep.isAtLeast(Step.Idle) }
                    )
                )
                add(ANIMATION_LOGO, AnimatorLogo())
                add(ANIMATION_RATING, AnimatorRating())
                add(ANIMATION_PAGE, AnimatorPage())
            }
        }
    }

    val logo get() = get(ANIMATION_LOGO) ?: throw IllegalStateException("forgot to add logo animation")
    val rating get() = get(ANIMATION_RATING) ?: throw IllegalStateException("forgot to add rating animation")
    val page get() = get(ANIMATION_PAGE) ?: throw IllegalStateException("forgot to add page animation")

    override val doneStep get() = Step.Done
    override val originStep get() = Step.Origin
    override val firstStep get() = Step.LogoAppear

    @Composable
    override fun onUpdateTransition() {
        this.step = rememberSaveable(stateSaver = Step.Saver) {
            mutableStateOf(MutableTransitionState(Step.Origin))
        }.value
    }

    @Composable
    override fun MutableTransitionState<Step>.updateStep() {
        when (currentState) {
            Step.LogoAppear ->
                targetState = Step.LogoRotate
            Step.LogoRotate ->
                targetState = Step.Idle
            Step.Idle ->
                targetState = Step.LogoTranslateAndPageAppear
            Step.LogoTranslateAndPageAppear -> {
                targetState = Step.Done
            }
            Step.Origin, Step.Done -> {}
        }
    }
}

class AnimatorLogo : AnimationCompound.Animator<Step> {
    private lateinit var translateFactor: State<Float>
    private lateinit var scaleProgress: State<Float>
    private lateinit var rotateProgress: State<Float>

    @Composable
    override fun update(transition: Transition<Step>) {
        translateFactor = transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = DURATION_LOGO_TRANSLATE_ms, easing = LinearEasing)
            },
            label = ""
        ) { step ->
            when (step.isAtLeast(Step.LogoTranslateAndPageAppear)) {
                false -> 1.0f
                else -> 0.0f
            }
        }
        scaleProgress = transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = DURATION_LOGO_APPEAR_ms, easing = LinearEasing)
            },
            label = ""
        ) { step ->
            when (step) {
                Step.Origin -> 0.15f
                Step.LogoAppear, Step.LogoRotate, Step.Idle -> 2.0f
                else -> 1.0f
            }
        }
        rotateProgress = transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = DURATION_LOGO_ROTATE_ms, easing = LinearEasing)
            },
            label = ""
        ) { step ->
            when (step.isAtLeast(Step.LogoRotate)) {
                false -> -90.0f
                else -> 0.0f
            }
        }
    }

    override fun animate(modifier: Modifier) = modifier
        .composed {
            val paddingVertical = MaterialTheme.dimensionsPaddingExtended.page.huge.vertical
            val paddingHorizontal = MaterialTheme.dimensionsPaddingExtended.page.huge.horizontal
            val pageSize = LocalActivity.size
            graphicsLayer {
                translationX =
                    (((pageSize.width.toPx() - size.width) / 2) * translateFactor.value) + paddingHorizontal.toPx()
                translationY =
                    (pageSize.height.toPx() - size.height) / 2 * translateFactor.value + paddingVertical.toPx()
                scaleX = scaleProgress.value
                scaleY = scaleProgress.value
                rotationZ = rotateProgress.value
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            }
        }
}

class AnimatorRating : AnimationCompound.AnimatorChild<Step, AnimatorRating.StepRating> {

    enum class StepRating {
        Small,
        Normal,
        Invisible
    }

    private lateinit var progress: State<Float>

    override fun createStepTransformer(): ((Step) -> StepRating) = { parentStep ->
        when (parentStep) {
            Step.Origin -> StepRating.Small
            Step.LogoAppear, Step.LogoRotate, Step.Idle -> StepRating.Normal
            else -> StepRating.Invisible
        }
    }

    @Composable
    override fun update(transition: Transition<StepRating>) {
        progress = transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = DURATION_LOGO_ROTATE_ms, easing = LinearEasing)
            },
            label = ""
        ) { step ->
            when (step) {
                StepRating.Small -> 0.5f
                StepRating.Normal -> 1.0f
                else -> 0.0f
            }
        }
    }

    override fun animate(modifier: Modifier) = modifier
        .alpha(progress.value)
        .scale(progress.value)
}

class AnimatorPage : AnimationCompound.AnimatorChild<Step, Boolean> {
    private lateinit var fadeFactor: State<Float>
    private lateinit var scaleProgress: State<Float>

    override fun createStepTransformer(): ((Step) -> Boolean) =
        { parentStep -> parentStep.isAtLeast(Step.LogoTranslateAndPageAppear) }

    @Composable
    override fun update(transition: Transition<Boolean>) {
        fadeFactor = transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = DURATION_PAGE_APPEAR_ms, easing = LinearEasing)
            },
            label = ""
        ) { step ->
            when (step) {
                false -> 0.0f
                else -> 1.0f
            }
        }
        scaleProgress = transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = DURATION_PAGE_APPEAR_ms, easing = LinearEasing)
            },
            label = ""
        ) { step ->
            when (step) {
                false -> 0.5f
                else -> 1.0f
            }
        }
    }

    override fun animate(modifier: Modifier) = modifier
        .alpha(fadeFactor.value)
        .scale(scaleProgress.value)
}