

package com.tezov.bank.ui.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tezov.bank.ui.component.element.CarouselCard
import com.tezov.lib_adr_ui_cpt.core.component.block.HorizontalPager
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Icon
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Text
import com.tezov.lib_adr_ui_core.modifier.thenOnNotNull
import com.tezov.lib_adr_ui_core.theme.style.OutfitText
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_kmm_core.delegate.DelegateNullFallBack

object SectionCarouselCard {

    class StyleBuilder internal constructor(
        style: Style
    ) {
        var styleIconInfo = style.styleIconInfo
        var outfitTextTitle = style.outfitTextTitle
        var colorBackgroundHeader = style.colorBackgroundHeader
        var colorBackgroundBody = style.colorBackgroundBody
        var styleCarousel = style.styleCarousel
        var styleCard = style.styleCard

        fun get() = Style(
            styleIconInfo = styleIconInfo,
            outfitTextTitle = outfitTextTitle,
            colorBackgroundHeader = colorBackgroundHeader,
            colorBackgroundBody = colorBackgroundBody,
            styleCarousel = styleCarousel,
            styleCard = styleCard,
        )
    }

    class Style(
        styleIconInfo: Icon.Simple.Style? = null,
        val outfitTextTitle: OutfitText.StateColor.Style? = null,
        val colorBackgroundHeader: Color? = null,
        val colorBackgroundBody: Color? = null,
        styleCarousel: HorizontalPager.WithIndicator.Style? = null,
        styleCard: CarouselCard.Style.Base? = null
    ) {

        val styleIconInfo: Icon.Simple.Style by DelegateNullFallBack.Ref(
            styleIconInfo,
            fallBackValue = {
                Icon.Simple.Style()
            }
        )
        val styleCarousel: HorizontalPager.WithIndicator.Style by DelegateNullFallBack.Ref(
            styleCarousel,
            fallBackValue = { HorizontalPager.WithIndicator.Style() }
        )
        val styleCard: CarouselCard.Style.Base by DelegateNullFallBack.Ref(
            styleCard,
            fallBackValue = { CarouselCard.Style.Button() }
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
            colorBackgroundHeader = style.colorBackgroundHeader,
            colorBackgroundBody = style.colorBackgroundBody,
            styleCarousel = style.styleCarousel,
            styleCard = style.styleCard,
        )

    }

    data class Data(
        val iconInfoId: Int? = null,
        val title: String? = null,
        val cards: List<CarouselCard.Data>
    )

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        style: Style,
        data: Data,
        onClick: (Int) -> Unit = {}
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .thenOnNotNull(style.colorBackgroundBody) {
                        background(it)
                    },
            ) {
                val cards = remember(data.cards) {
                    (ArrayList<@Composable () -> Unit>()).apply {
                        data.cards.forEachIndexed { index, card ->
                            add {
                                CarouselCard(
                                    data = card,
                                    style = style.styleCard,
                                    onClick = {
                                        onClick(index)
                                    }
                                )
                            }
                        }
                    }
                }
                HorizontalPager.WithIndicator(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = style.styleCarousel,
                    items = cards
                )
            }
        }
    }

}