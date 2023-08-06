

package com.tezov.bank.ui.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tezov.bank.ui.component.element.RollerCard
import com.tezov.lib_adr_sdk_core.ui.component.block.HorizontalRoller
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Button
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Icon
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Text
import com.tezov.lib_adr_sdk_core.ui.modifier.thenOnNotNull
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitText
import com.tezov.lib_adr_sdk_core.ui.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_adr_core.delegate.DelegateNullFallBack

object SectionRollerCard {

    class StyleBuilder internal constructor(
        style: Style
    ) {
        var styleIconInfo = style.styleIconInfo
        var outfitTextTitle = style.outfitTextTitle
        var outfitTextSubTitle = style.outfitTextSubTitle
        var colorBackgroundHeader = style.colorBackgroundHeader
        var colorBackgroundBody = style.colorBackgroundBody
        var styleAction = style.styleAction
        var styleRoller = style.styleRoller
        var styleCard = style.styleCard

        fun get() = Style(
            styleIconInfo = styleIconInfo,
            outfitTextTitle = outfitTextTitle,
            outfitTextSubTitle = outfitTextSubTitle,
            colorBackgroundHeader = colorBackgroundHeader,
            colorBackgroundBody = colorBackgroundBody,
            styleAction = styleAction,
            styleRoller = styleRoller,
            styleCard = styleCard,
        )
    }

    class Style(
        styleIconInfo: Icon.Simple.Style? = null,
        val outfitTextTitle: OutfitText.StateColor.Style? = null,
        val outfitTextSubTitle: OutfitText.StateColor.Style? = null,
        val colorBackgroundHeader: Color? = null,
        val colorBackgroundBody: Color? = null,
        styleAction: Button.StateColor.Style? = null,
        styleRoller: HorizontalRoller.Simple.Style? = null,
        styleCard: RollerCard.Style? = null
    ) {

        val styleIconInfo: Icon.Simple.Style by DelegateNullFallBack.Ref(
            styleIconInfo,
            fallBackValue = {
                Icon.Simple.Style()
            }
        )
        val styleAction: Button.StateColor.Style by DelegateNullFallBack.Ref(
            styleAction,
            fallBackValue = { Button.StateColor.Style() }
        )
        val styleRoller: HorizontalRoller.Simple.Style by DelegateNullFallBack.Ref(
            styleRoller,
            fallBackValue = { HorizontalRoller.Simple.Style() }
        )
        val styleCard: RollerCard.Style by DelegateNullFallBack.Ref(
            styleCard,
            fallBackValue = { RollerCard.Style() }
        )

        companion object {

            @Composable
            fun Style.copy(
                scope: @Composable StyleBuilder.() -> Unit = {}
            ) = StyleBuilder(this).also {
                it.scope()
            }.get()

        }

        constructor(style: Style) : this(
            styleIconInfo = style.styleIconInfo,
            outfitTextTitle = style.outfitTextTitle,
            outfitTextSubTitle = style.outfitTextSubTitle,
            colorBackgroundHeader = style.colorBackgroundHeader,
            colorBackgroundBody = style.colorBackgroundBody,
            styleAction = style.styleAction,
            styleRoller = style.styleRoller,
            styleCard = style.styleCard,
        )

    }

    data class Data(
        val iconInfoId: Int? = null,
        val title: String? = null,
        val subTitle: String? = null,
        val action: String? = null,
        val cards: List<RollerCard.Data>
    )


    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        style: Style,
        data: Data,
        onClickCard: ((Int) -> Unit)? = null,
        onClickButton: () -> Unit = {}
    ) {
        if (data.cards.isEmpty()) {
            return
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            data.title?.let { text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .thenOnNotNull(style.colorBackgroundHeader) {
                            modifier.background(it)
                        }
                        .padding(start = MaterialTheme.dimensionsPaddingExtended.element.small.horizontal),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    data.iconInfoId?.let {
                        Icon.Simple(
                            modifier = Modifier
                                .padding(end = MaterialTheme.dimensionsPaddingExtended.element.small.horizontal),
                            style = style.styleIconInfo,
                            resourceId = it,
                            description = text,
                        )
                    }
                    Text.StateColor(
                        modifier = Modifier.padding(vertical = MaterialTheme.dimensionsPaddingExtended.element.big.vertical),
                        text = text,
                        style = style.outfitTextTitle
                    )
                }
            }
            data.subTitle?.let { text ->
                Text.StateColor(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.dimensionsPaddingExtended.element.small.horizontal,
                            bottom = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical
                        ),
                    text = text,
                    style = style.outfitTextSubTitle
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .thenOnNotNull(style.colorBackgroundBody) {
                        background(it)
                    },
            ) {
                val cards = remember(data.cards) {
                    (ArrayList<@Composable LazyItemScope.() -> Unit>()).apply {
                        data.cards.forEachIndexed { index, card ->
                            add {
                                RollerCard(
                                    modifier = Modifier.fillMaxSize(),
                                    data = card,
                                    style = style.styleCard,
                                    onClick = onClickCard?.let { { it(index) } }
                                )
                            }
                        }
                    }
                }
                HorizontalRoller.Simple(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = style.styleRoller,
                    items = cards,
                )
            }
            data.action?.let {
                Button.StateColor(
                    modifierButton = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterHorizontally)
                        .padding(
                            top = MaterialTheme.dimensionsPaddingExtended.element.big.vertical
                        ),
                    text = data.action,
                    style = style.styleAction,
                    onClick = onClickButton,
                )
            }

        }
    }

}