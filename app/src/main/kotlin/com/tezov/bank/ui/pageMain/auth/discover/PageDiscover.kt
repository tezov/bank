

package com.tezov.bank.ui.pageMain.auth.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tezov.bank.ui.component.block.*
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppController
import com.tezov.lib_adr_sdk_core.navigation.navigator.GraphEntry
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Text
import com.tezov.lib_adr_sdk_core.ui.compositionTree.page.Page
import com.tezov.lib_adr_sdk_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_adr_sdk_core.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_adr_sdk_core.ui.extension.ExtensionCompositionLocal
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.theme.dimensionsPaddingExtended

object PageDiscover : Page<PageDiscoverState, PageDiscoverAction> {

    @Composable
    override fun Page<PageDiscoverState, PageDiscoverAction>.content(graphEntry: GraphEntry, innerPadding: PaddingValues) {
        val accessor = DiAccessorAppUiPage().with(key = this).contextDiscover().apply {
            remember()
        }
        val action = accessor.action()
        val state = accessor.state()

        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PageDiscoverTheme provides PageDiscoverTheme.provideColors(),
                PageDiscoverTheme provides PageDiscoverTheme.provideDimensions(),
            ),
            parent = {
                arrayOf(
                    PageDiscoverTheme provides PageDiscoverTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    PageDiscoverTheme provides PageDiscoverTheme.provideStyles(),
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(PageDiscoverTheme.colors.background)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PageDiscoverTheme.colors.accent)
                        .height(PageDiscoverTheme.dimensions.header),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    contentHeader(state.header)
                    contentPager(
                        action = action,
                        cardsWithButton = state.cardsWithButton,
                        cardsWithLink = state.cardsWithLink,
                        cashbacks = state.cashbacks
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.big.vertical))
                    contentSection(
                        action = action,
                        offers = state.offers,
                        tips = state.tips
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.big.vertical))
                }
            }
        }
    }

    @Composable
    private fun ColumnScope.contentHeader(
        header: PageDiscoverState.Header?
    ) {
        if (header == null) {
            return
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.dimensionsPaddingExtended.page.normal.vertical,
                    start = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal,
                    end = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal,
                )
        ) {
            header.headline?.let {
                Text.StateColor(
                    text = it,
                    style = PageDiscoverTheme.typographies.headline.copy {
                        outfitState = PageDiscoverTheme.colors.background.asStateSimple
                    }
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.contentPager(
        action: PageDiscoverAction,
        cardsWithButton: SectionCarouselCard.Data?,
        cardsWithLink: SectionCarouselCard.Data?,
        cashbacks: SectionRollerCard.Data?,
    ) {
        cardsWithButton?.let {
            SectionCarouselCard(
                data = it,
                style = PageDiscoverTheme.styles.sectionCarouselCardButton,
                onClick = action::onClickCardsWithButton
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.big.vertical))
        cardsWithLink?.let {
            SectionCarouselCard(
                data = it,
                style = PageDiscoverTheme.styles.sectionCarouselCardLink,
                onClick = action::onClickCardsWithLink
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.big.vertical))
        cashbacks?.let {
            SectionRollerCard(
                data = it,
                style = PageDiscoverTheme.styles.sectionRollerCard,
                onClickCard = action::onClickCashbacksCard,
                onClickButton = action::onClickCashbacksButton
            )
        }
    }

    @Composable
    private fun ColumnScope.contentSection(
        action: PageDiscoverAction,
        offers: SectionSimpleTile.Data?,
        tips: SectionSimpleRow.Data?,
    ) {
        offers?.let {
            SectionSimpleTile(
                data = it,
                style = PageDiscoverTheme.styles.sectionActionCard,
                onClick = action::onClickOffers
            )
        }
        Spacer(modifier = Modifier.height(PageDiscoverTheme.dimensions.spacingTopSectionRowToBottomSectionCard))
        tips?.let {
            SectionSimpleRow(
                data = it,
                style = PageDiscoverTheme.styles.sectionActionRow,
                onClick = action::onClickTips
            )
        }
    }

    @Composable
    override fun onBackButtonPressed() = DialogCloseAppController.handleOnBackPressed()

}