

package com.tezov.bank.ui.pageMain.auth.help

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tezov.bank.ui.component.block.SectionSimpleRow
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppController
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Text
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_core_android_kotlin.ui.extension.ExtensionCompositionLocal
import com.tezov.lib_core_android_kotlin.ui.theme.theme.dimensionsPaddingExtended

object PageHelp : Page<PageHelpState, PageHelpAction> {

    @Composable
    override fun Page<PageHelpState, PageHelpAction>.content(graphEntry: GraphEntry, innerPadding: PaddingValues) {
        val accessor = DiAccessorAppUiPage().with(key = this).contextHelp().apply {
            remember()
        }
        val action = accessor.action()
        val state = accessor.state()
        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PageHelpTheme provides PageHelpTheme.provideColors(),
            ),
            parent = {
                arrayOf(
                    PageHelpTheme provides PageHelpTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    PageHelpTheme provides PageHelpTheme.provideStyles(),
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PageHelpTheme.colors.background)
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                contentHeader(state.header)
                contentBody(
                    action= action,
                    emergencies = state.emergencies,
                    paymentModes = state.paymentModes,
                    configuration = state.configuration,
                    account = state.accounts,
                    misc = state.misc,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.huge.vertical))
            }
        }
    }

    @Composable
    private fun ColumnScope.contentHeader(
        header: PageHelpState.Header?
    ) {
        if(header == null){
            return
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.dimensionsPaddingExtended.page.normal.vertical,
                    start = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal,
                    end = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal,
                    bottom = MaterialTheme.dimensionsPaddingExtended.block.huge.vertical
                )
        ) {
            header.headline?.let {
                Text.StateColor(
                    text = it,
                    style = PageHelpTheme.typographies.headline
                )
            }
            header.title?.let {
                Text.StateColor(
                    modifier = Modifier.padding(top = MaterialTheme.dimensionsPaddingExtended.block.huge.vertical),
                    text = it,
                    style = PageHelpTheme.typographies.subHeadline
                )
            }
            header.body?.let {
                Text.StateColor(
                    modifier = Modifier.padding(top = MaterialTheme.dimensionsPaddingExtended.block.normal.vertical),
                    text = it,
                    style = PageHelpTheme.typographies.body
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.contentBody(
        action: PageHelpAction,
        emergencies: SectionSimpleRow.Data?,
        paymentModes: SectionSimpleRow.Data?,
        configuration: SectionSimpleRow.Data?,
        account: SectionSimpleRow.Data?,
        misc: SectionSimpleRow.Data?,
    ) {
        emergencies?.let {
            SectionSimpleRow(data = it, style = PageHelpTheme.styles.sectionRow, onClick = action::onClickEmergencies)
        }
        paymentModes?.let {
            SectionSimpleRow(data = it, style = PageHelpTheme.styles.sectionRow, onClick = action::onClickPaymentModes)
        }
        configuration?.let {
            SectionSimpleRow(data = it, style = PageHelpTheme.styles.sectionRow, onClick = action::onClickConfigurations)
        }
        account?.let {
            SectionSimpleRow(data = it, style = PageHelpTheme.styles.sectionRow, onClick = action::onClickAccounts)
        }
        misc?.let {
            SectionSimpleRow(data = it, style = PageHelpTheme.styles.sectionRow, onClick = action::onClickMisc)
        }

    }

    @Composable
    override fun onBackButtonPressed() = DialogCloseAppController.handleOnBackPressed()

}