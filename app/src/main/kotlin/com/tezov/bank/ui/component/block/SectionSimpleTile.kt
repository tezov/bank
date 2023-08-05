

package com.tezov.bank.ui.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tezov.bank.ui.component.element.SimpleTile
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Icon
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Text
import com.tezov.lib_adr_sdk_core.ui.extension.ExtensionComposable
import com.tezov.lib_adr_sdk_core.ui.extension.ExtensionComposable.loopOver
import com.tezov.lib_adr_sdk_core.ui.modifier.then
import com.tezov.lib_adr_sdk_core.ui.modifier.thenOnNotNull
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitText
import com.tezov.lib_adr_sdk_core.ui.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_adr_core.delegate.DelegateNullFallBack

object SectionSimpleTile {

    class StyleBuilder internal constructor(
        style: Style
    ) {
        var styleIconInfo = style.styleIconInfo
        var outfitTextTitle = style.outfitTextTitle
        var colorBackgroundHeader = style.colorBackgroundHeader
        var colorBackgroundBody = style.colorBackgroundBody
        var paddingBody = style.paddingBody
        var styleTile = style.styleTile

        fun get() = Style(
            styleIconInfo = styleIconInfo,
            outfitTextTitle = outfitTextTitle,
            colorBackgroundHeader = colorBackgroundHeader,
            colorBackgroundBody = colorBackgroundBody,
            paddingBody = paddingBody,
            styleTile = styleTile,
        )
    }

    class Style(
        styleIconInfo: Icon.Simple.Style? = null,
        val outfitTextTitle: OutfitText.StateColor.Style? = null,
        val colorBackgroundHeader: Color? = null,
        val colorBackgroundBody: Color? = null,
        val paddingBody: Dp = 0.dp,
        styleTile: SimpleTile.Style? = null
    ) {

        val styleIconInfo: Icon.Simple.Style by DelegateNullFallBack.Ref(
            styleIconInfo,
            fallBackValue = {
                Icon.Simple.Style()
            }
        )
        val styleTile: SimpleTile.Style by DelegateNullFallBack.Ref(
            styleTile,
            fallBackValue = { SimpleTile.Style() }
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
            paddingBody = style.paddingBody,
            styleTile = style.styleTile,
        )

    }

    data class Data(
        val iconInfoId: Int? = null,
        val title: String? = null,
        var template: SimpleTile.Template = SimpleTile.Template.Undefined,
        val tile: List<SimpleTile.Data>
    )

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        style: Style,
        data: Data,
        onClick: (Int) -> Unit = {}
    ) {
        if (data.tile.isEmpty()) {
            return
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            data.title?.let { text ->
                var headerHasBackground = false
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .thenOnNotNull(style.colorBackgroundHeader) {
                            headerHasBackground = true
                            background(it)
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
                if(headerHasBackground) {
                    Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.block.big.vertical))
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .thenOnNotNull(style.colorBackgroundBody) {
                        background(it)
                    }
                    .then(style.colorBackgroundHeader, onNotNull = {
                        padding(vertical = MaterialTheme.dimensionsPaddingExtended.element.huge.vertical)
                    }, onNull = {
                        padding(vertical = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical)
                    })
                    .padding(horizontal = style.paddingBody),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensionsPaddingExtended.block.big.vertical),
            ) {
                data.tile.loopOver {
                    val first = nextOrNull
                    val second = nextOrNull
                    // set default section template
                    if (data.template.isDefined) {
                        first?.let {
                            if (first.data.template.isUndefined) {
                                first.data.template = data.template
                            }
                        }
                        second?.let {
                            if (second.data.template.isUndefined) {
                                second.data.template = data.template
                            }
                        }
                    }
                    // compose row
                    if (first != null && second != null) {
                        if (first.data.template.isInlined) {
                            push(second)
                            ContentRowUno(style.styleTile, first, onClick)
                        } else if (second.data.template.isInlined) {
                            push(first)
                            ContentRowUno(style.styleTile, second, onClick)
                        } else {
                            ContentRowDuo(style.styleTile, first, second, onClick)
                        }
                        if (hasReachEnd && isStackEmpty) {
                            done()
                        }
                    } else if (first != null) {
                        ContentRowUno(style.styleTile, first, onClick)
                        done()
                    } else {
                        done()
                    }
                }
            }
        }
    }

    @Composable
    private fun ContentRowUno(
        style: SimpleTile.Style,
        first: ExtensionComposable.LoopOver.Entry<SimpleTile.Data>,
        onClick: (Int) -> Unit
    ) {
        SimpleTile(
            modifier = Modifier
                .fillMaxWidth(),
            style = style,
            data = first.data.apply {
                template = SimpleTile.Template.InlineDefault
            },
            onClick = { onClick(first.index) }
        )
    }

    @Composable
    private fun ContentRowDuo(
        style: SimpleTile.Style,
        first: ExtensionComposable.LoopOver.Entry<SimpleTile.Data>,
        second: ExtensionComposable.LoopOver.Entry<SimpleTile.Data>,
        onClick: (Int) -> Unit
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensionsPaddingExtended.block.big.vertical),
        ) {
            SimpleTile(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                style = style,
                data = first.data,
                onClick = { onClick(first.index) }
            )
            SimpleTile(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                style = style,
                data = second.data,
                onClick = { onClick(second.index) }
            )
        }

    }

}