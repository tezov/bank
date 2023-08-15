

package com.tezov.bank.ui.pageMain.auth.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleTile
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppController
import com.tezov.lib_adr_app_core.navigation.navigator.GraphEntry
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Shadow
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Text
import com.tezov.lib_adr_ui_cpt.component.core.layout.ColumnCollapsibleHeader
import com.tezov.lib_adr_app_core.ui.compositionTree.page.Page
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_adr_ui_core.extension.ExtensionCompositionLocal
import com.tezov.lib_adr_ui_core.modifier.then
import com.tezov.lib_adr_ui_core.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsCommonExtended
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended

object PagePayment : Page<PagePaymentState, PagePaymentAction> {

    private const val DIVIDER_HEADER_VISIBILITY_START = 0.3f

    @Composable
    override fun Page<PagePaymentState, PagePaymentAction>.content(graphEntry: GraphEntry, innerPadding: PaddingValues) {
        val accessor = DiAccessorAppUiPage().with(key = this).contextPayment().apply {
            remember()
        }
        val action = accessor.action()
        val state = accessor.state()
        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PagePaymentTheme provides PagePaymentTheme.provideColors(),
                PagePaymentTheme provides PagePaymentTheme.provideDimensions(),
            ),
            parent = {
                arrayOf(
                    PagePaymentTheme provides PagePaymentTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    PagePaymentTheme provides PagePaymentTheme.provideStyles(),
                )
            }
        ) {
            ColumnCollapsibleHeader(
                modifier = Modifier
                    .background(PagePaymentTheme.colors.background)
                    .padding(innerPadding),
                properties = PagePaymentTheme.dimensions.headerProperties,
                header = { progress, progressDp ->
                    contentHeader(
                        state.header,
                        progress,
                        progressDp
                    )
                },
                body = {
                    contentBody(
                        action = action,
                        cardSmall = state.cardsSmall,
                        cardLarge = state.cardsLarge,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.huge.vertical))
                }
            )
        }
    }

    @Composable
    private fun BoxScope.contentHeader(
        header: PagePaymentState.Header?,
        progress: Float,
        progressDp: Dp
    ) {
        if(header == null){
            return
        }
        header.headline?.let {
            Column(
                modifier = Modifier.height(progressDp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text.StateColor(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal + (MaterialTheme.dimensionsPaddingExtended.element.huge.horizontal * progress),
                            vertical = MaterialTheme.dimensionsPaddingExtended.page.normal.vertical
                        ),
                    text = it,
                    style = PagePaymentTheme.typographies.headline.copy {
                        typo = typo.copy(
                            fontSize = (PagePaymentTheme.dimensions.headLineMin.value + ((PagePaymentTheme.dimensions.headlineMax.value - PagePaymentTheme.dimensions.headLineMin.value) * progress)).sp
                        )
                    }
                )
                Shadow.Line(
                    modifier = Modifier
                        .then(progress < DIVIDER_HEADER_VISIBILITY_START,
                            onTrue = {
                                alpha((DIVIDER_HEADER_VISIBILITY_START - progress) / DIVIDER_HEADER_VISIBILITY_START)
                            },
                            onFalse = {
                                alpha(0f)
                            }
                        ),
                    elevation = (MaterialTheme.dimensionsCommonExtended.elevation.normal * (1-progress)),
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.contentBody(
        action: PagePaymentAction,
        cardSmall: SectionSimpleTile.Data?,
        cardLarge: SectionSimpleTile.Data?,
    ){
        cardSmall?.let {
            SectionSimpleTile(
                data = it,
                style = PagePaymentTheme.styles.sectionCard,
                onClick = action::onClickCardsSmall
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.block.huge.vertical))
        cardLarge?.let {
            SectionSimpleTile(
                data = it,
                style = PagePaymentTheme.styles.sectionCard,
                onClick = action::onClickCardsLarge
            )
        }
    }

    @Composable
    override fun onBackButtonPressed() = DialogCloseAppController.handleOnBackPressed()
}