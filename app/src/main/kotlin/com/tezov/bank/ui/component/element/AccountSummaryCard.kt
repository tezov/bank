

package com.tezov.bank.ui.component.element

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import com.tezov.bank.ui.pageMain.lobby.lounge.*
import com.tezov.lib_adr_sdk_core.ui.component.chunk.DropDownMenu
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Icon
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Text
import com.tezov.lib_adr_sdk_core.ui.modifier.*
import com.tezov.lib_adr_sdk_core.ui.theme.style.*
import com.tezov.lib_adr_sdk_core.ui.theme.theme.ThemeColorsExtended
import com.tezov.lib_adr_sdk_core.ui.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_kmm_core.delegate.DelegateNullFallBack
import java.util.*

object AccountSummaryCard {

    private const val MIN_SHRINK_FACTOR = 0.60f

    class StyleBuilder internal constructor(
        style: Style
    ) {
        var outfitFrame = style.outfitFrame
        var styleIconInfo = style.styleIconInfo
        var outfitTextSurtitle = style.outfitTextSurtitle
        var outfitTextTitle = style.outfitTextTitle
        var outfitTextSubtitle = style.outfitTextSubtitle
        var outfitTextAmount = style.outfitTextAmount
        var styleDropDownMenu = style.styleDropDownMenu

        internal fun get() = Style(
            outfitFrame = outfitFrame,
            styleIconInfo = styleIconInfo,
            outfitTextSurtitle = outfitTextSurtitle,
            outfitTextTitle = outfitTextTitle,
            outfitTextSubtitle = outfitTextSubtitle,
            outfitTextAmount = outfitTextAmount,
            styleDropDownMenu = styleDropDownMenu,
        )
    }

    class Style(
        outfitFrame: OutfitFrameStateColor? = null,
        styleIconInfo: Icon.Simple.Style? = null,
        val outfitTextSurtitle: OutfitTextStateColor? = null,
        val outfitTextTitle: OutfitTextStateColor? = null,
        val outfitTextSubtitle: OutfitTextStateColor? = null,
        val outfitTextAmount: OutfitTextStateColor? = null,
        styleDropDownMenu: DropDownMenu.StateColor.Style? = null,
    ) {
        val outfitFrame: OutfitFrameStateColor by DelegateNullFallBack.Ref(
            outfitFrame,
            fallBackValue = {
                ThemeColorsExtended.Dummy.outfitFrameState
            }
        )
        val styleIconInfo: Icon.Simple.Style by DelegateNullFallBack.Ref(
            styleIconInfo,
            fallBackValue = {
                Icon.Simple.Style()
            }
        )
        val styleDropDownMenu: DropDownMenu.StateColor.Style by DelegateNullFallBack.Ref(
            styleDropDownMenu,
            fallBackValue = {
                DropDownMenu.StateColor.Style()
            }
        )

        companion object {

            @Composable
            fun Style.copy(
                builder: @Composable StyleBuilder.() -> Unit = {}
            ) = StyleBuilder(this).also {
                it.builder()
            }.get()

        }

        constructor(style: Style?) : this(
            outfitFrame = style?.outfitFrame,
            styleIconInfo = style?.styleIconInfo,
            outfitTextSurtitle = style?.outfitTextSurtitle,
            outfitTextTitle = style?.outfitTextTitle,
            outfitTextSubtitle = style?.outfitTextSubtitle,
            outfitTextAmount = style?.outfitTextAmount,
            styleDropDownMenu = style?.styleDropDownMenu,
        )

    }

    data class Data(
        val iconInfoId: Int,
        val iconActionId: Int,
        val surtitle: String,
        val title: String,
        val subTitle: String,
        val amount: String,
        val actions: List<String>,
    )

    private enum class MotionLayoutItem {
        ICON_INFO,
        ICON_ACTION,
        SURTITLE,
        TITLE,
        SUBTITLE,
        AMOUNT
    }

    @Composable
    private fun constraintSetStart() = remember {
        ConstraintSet {
            val iconInfo = createRefFor(MotionLayoutItem.ICON_INFO.name)
            val iconAction = createRefFor(MotionLayoutItem.ICON_ACTION.name)
            val surtitle = createRefFor(MotionLayoutItem.SURTITLE.name)
            val title = createRefFor(MotionLayoutItem.TITLE.name)
            val subtitle = createRefFor(MotionLayoutItem.SUBTITLE.name)
            val amount = createRefFor(MotionLayoutItem.AMOUNT.name)
            constrain(iconAction) {
                width = Dimension.wrapContent
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
            constrain(iconInfo) {
                width = Dimension.wrapContent
                top.linkTo(parent.top)
                end.linkTo(iconAction.start)
            }
            constrain(surtitle) {
                width = Dimension.fillToConstraints
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(iconInfo.start)
            }
            constrain(title) {
                top.linkTo(surtitle.bottom)
                start.linkTo(parent.start)
            }
            constrain(subtitle) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            }
            constrain(amount) {
                top.linkTo(subtitle.bottom)
                start.linkTo(parent.start)
            }
        }
    }

    @Composable
    private fun constraintSetEnd() = remember {
        ConstraintSet {
            val iconInfo = createRefFor(MotionLayoutItem.ICON_INFO.name)
            val iconAction = createRefFor(MotionLayoutItem.ICON_ACTION.name)
            val surtitle = createRefFor(MotionLayoutItem.SURTITLE.name)
            val title = createRefFor(MotionLayoutItem.TITLE.name)
            val subtitle = createRefFor(MotionLayoutItem.SUBTITLE.name)
            val amount = createRefFor(MotionLayoutItem.AMOUNT.name)
            constrain(iconAction) {
                width = Dimension.wrapContent
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
            constrain(iconInfo) {
                width = Dimension.wrapContent
                top.linkTo(parent.top)
                end.linkTo(iconAction.start)
            }
            constrain(surtitle) {
                width = Dimension.fillToConstraints
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(iconInfo.start)
            }
            constrain(title) {
                width = Dimension.fillToConstraints
                top.linkTo(surtitle.bottom)
                start.linkTo(parent.start)
                end.linkTo(amount.start)
            }
            constrain(subtitle) {
                visibility = Visibility.Invisible
                scaleX = 0.0f
                scaleY = 0.0f
                translationY = (-20).dp
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            }
            constrain(amount) {
                width = Dimension.wrapContent
                top.linkTo(title.top)
                start.linkTo(title.end)
                end.linkTo(parent.end)
                bottom.linkTo(title.bottom)
            }
        }
    }

    @Composable
    private fun constraintTransition() = Transition(
        """{
            pathMotionArc: 'startVertical',
            easing: 'easeInOut',
            KeyFrames: {
                KeyAttributes: [
                    {
                        target: ['AMOUNT'],
                        frames: [0, 50, 100],
                        translationY: [+25, -80, 0],
                        translationX: [-30, 0, 0],
                        scaleY: [0.75, 0.65, 1.0],
                        scaleX: [0.75, 0.5, 1.0],
                        alpha: [1.0, 0.5, 1.0],
                    }
                ]
            }
    }""".trimIndent()
    )

    @OptIn(ExperimentalMotionApi::class)
    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        style: Style,
        data: Data,
        progress: Float = 1.0f,
        onClick: ((Int) -> Unit) = {}
    ) {
        val heightState = remember {
            mutableStateOf(Dp.Unspecified)
        }
        MotionLayout(
            modifier = modifier
                .fillMaxWidth()
                .updateToMaxHeight(heightState, heightState.value == Dp.Unspecified)
                .thenOnTrue(heightState.value != Dp.Unspecified) {
                    height(heightState.value * (progress).coerceAtLeast(MIN_SHRINK_FACTOR))
                }
                .background(style.outfitFrame)
                .padding(MaterialTheme.dimensionsPaddingExtended.block.huge),
            start = constraintSetEnd(),
            end = constraintSetStart(),
            transition = constraintTransition(),
            progress = progress,
//            debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL)
        ) {
            Icon.Simple(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimensionsPaddingExtended.element.small.horizontal)
                    .layoutId(MotionLayoutItem.ICON_INFO.name),
                style = style.styleIconInfo,
                resourceId = data.iconInfoId,
                description = data.title
            )
            DropDownMenu.StateColor(
                modifierBox = Modifier
                    .layoutId(MotionLayoutItem.ICON_ACTION.name),
                style =  style.styleDropDownMenu,
                resourceId = data.iconActionId,
                description = data.surtitle,
                items = data.actions,
                offset = DpOffset(Dp.Infinity,0.dp),
                onClick = onClick
            )
            Text.StateColor(
                modifier = Modifier
                    .layoutId(MotionLayoutItem.SURTITLE.name),
                text = data.surtitle,
                style = style.outfitTextSurtitle
            )
            Text.StateColor(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical)
                    .layoutId(MotionLayoutItem.TITLE.name),
                text = data.title,
                style = style.outfitTextTitle,
                maxLines = 1
            )
            Text.StateColor(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical)
                    .layoutId(MotionLayoutItem.SUBTITLE.name),
                text = data.subTitle,
                style = style.outfitTextSubtitle
            )
            Text.StateColor(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical)
                    .layoutId(MotionLayoutItem.AMOUNT.name),
                text = data.amount,
                style = style.outfitTextAmount
            )
        }

    }


}




