

package com.tezov.bank.ui.pageMain.auth.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tezov.app.R
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleRow
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppController
import com.tezov.lib_adr_app_core.navigation.navigator.ComposableNavigator
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Icon
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Image
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Text
import com.tezov.lib_adr_app_core.ui.compositionTree.page.Page
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_adr_ui_core.extension.ExtensionCompositionLocal
import com.tezov.lib_adr_ui_core.theme.style.padding
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended

object PageProfile : Page<PageProfileState, PageProfileAction> {

    @Composable
    override fun Page<PageProfileState, PageProfileAction>.content(graphEntry: ComposableNavigator.GraphEntry, innerPadding: PaddingValues) {
        val accessor = DiAccessorAppUiPage().with(key = this).contextProfile().apply {
            remember()
        }
        val action = accessor.action()
        val state = accessor.state()
        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PageProfileTheme provides PageProfileTheme.provideColors(),
                PageProfileTheme provides PageProfileTheme.provideDimensions(),
            ),
            parent = {
                arrayOf(
                    PageProfileTheme provides PageProfileTheme.provideShapes(),
                    PageProfileTheme provides PageProfileTheme.provideBorders(),
                    PageProfileTheme provides PageProfileTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    PageProfileTheme provides PageProfileTheme.provideStyles(),
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PageProfileTheme.colors.background)
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                contentHeader(
                    action = action,
                    header = state.header
                )
                contentBody(
                    action = action,
                    profils = state.profils,
                    documents = state.documents,
                    offers = state.offers,
                    helps = state.helps,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.huge.vertical))
            }
        }
    }

    @Composable
    private fun ColumnScope.contentHeader(
        action: PageProfileAction,
        header: PageProfileState.Header?
    ) {
        if (header == null) {
            return
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon.Clickable(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.dimensionsPaddingExtended.page.normal.vertical,
                        vertical = MaterialTheme.dimensionsPaddingExtended.page.normal.vertical
                    )
                    .align(Alignment.End),
                radiusRipple = PageProfileTheme.styles.iconLogOut.size?.radiusMin,
                colorRipple = PageProfileTheme.styles.iconLogOut.tint,
                onClick = action::onClickExit
            ) {
                Icon.StateColor(
                    style = PageProfileTheme.styles.iconLogOut,
                    resourceId = R.drawable.ic_logout_24dp,
                    description = stringResource(id = R.string.pg_profile_icon_close),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimensionsPaddingExtended.page.big)
            ) {
                header.imageId?.let {
                    Image.StateColor(
                        style = PageProfileTheme.styles.iconUser,
                        resourceId = it,
                        description = stringResource(id = R.string.pg_profile_icon_use)
                    )
                }
                header.name?.let {
                    Text.StateColor(
                        modifier = Modifier
                            .align(Alignment.Top)
                            .padding(start = MaterialTheme.dimensionsPaddingExtended.element.huge.horizontal),
                        text = it,
                        style = PageProfileTheme.typographies.title
                    )
                }
            }
        }
    }

    @Composable
    private fun ColumnScope.contentBody(
        action: PageProfileAction,
        profils: SectionSimpleRow.Data?,
        documents: SectionSimpleRow.Data?,
        offers: SectionSimpleRow.Data?,
        helps: SectionSimpleRow.Data?,
    ) {
        profils?.let {
            SectionSimpleRow(
                data = it,
                style = PageProfileTheme.styles.sectionRow,
                onClick = action::onClickProfiles
            )
        }
        documents?.let {
            SectionSimpleRow(
                data = it,
                style = PageProfileTheme.styles.sectionRow,
                onClick = action::onClickDocuments
            )
        }
        offers?.let {
            SectionSimpleRow(
                data = it,
                style = PageProfileTheme.styles.sectionRow,
                onClick = action::onClickOffers
            )
        }
        helps?.let {
            SectionSimpleRow(
                data = it,
                style = PageProfileTheme.styles.sectionRow,
                onClick = action::onClickHelps
            )
        }
    }

    @Composable
    override fun onBackButtonPressed() = DialogCloseAppController.handleOnBackPressed()

}