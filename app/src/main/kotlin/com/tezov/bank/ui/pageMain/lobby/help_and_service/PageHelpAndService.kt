

package com.tezov.bank.ui.pageMain.lobby.help_and_service

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tezov.app.R
import com.tezov.bank.ui.component.block.SectionSimpleTile
import com.tezov.bank.ui.component.block.SectionSimpleRow
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry
import com.tezov.lib_core_android_kotlin.ui.type.primaire.size
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Icon
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Shadow
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Text
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_core_android_kotlin.ui.extension.ExtensionCompositionLocal
import com.tezov.lib_core_android_kotlin.ui.theme.style.padding
import com.tezov.lib_core_android_kotlin.ui.theme.theme.*

object PageHelpAndService : Page<PageHelpAndServiceState, PageHelpAndServiceAction> {

    @Composable
    override fun Page<PageHelpAndServiceState, PageHelpAndServiceAction>.content(graphEntry: GraphEntry, innerPadding: PaddingValues) {
        val accessor = DiAccessorAppUiPage().with(key = this).contextHelpAndService().apply {
            remember()
        }
        val state = accessor.state()
        val action = accessor.action()
        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PageHelpAndServiceTheme provides PageHelpAndServiceTheme.provideColors(),
            ),
            parent = {
                arrayOf(
                    PageHelpAndServiceTheme provides PageHelpAndServiceTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    PageHelpAndServiceTheme provides PageHelpAndServiceTheme.provideStyles()
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorsExtended.background.default)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon.Clickable(
                        modifier = Modifier
                            .align(Alignment.Start),
                        radiusRipple = MaterialTheme.dimensionsIconExtended.modal.normal.radiusMin,
                        colorRipple = PageHelpAndServiceTheme.colors.accent,
                        onClick = action::onClickClose
                    ) {
                        Icon(
                            modifier = Modifier.size(MaterialTheme.dimensionsIconExtended.modal.normal),
                            painter = painterResource(id = R.drawable.ic_close_24dp),
                            contentDescription = stringResource(id = R.string.pg_h_and_s_icon_close),
                            tint = PageHelpAndServiceTheme.colors.accent,
                        )
                    }
                    Shadow.Line(elevation = MaterialTheme.dimensionsCommonExtended.elevation.normal)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    contentHeader(state.header)
                    contentBody(
                        action = action,
                        helpAndServices = state.helpAndServices,
                        contacts = state.contacts,
                        notices = state.notices,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.huge.vertical))
                }
            }
        }
    }

    @Composable
    private fun ColumnScope.contentHeader(
        header: PageHelpAndServiceState.Header?
    ) {
        if(header == null){
            return
        }
        header.headline?.let {
            Text.StateColor(
                modifier = Modifier.padding(MaterialTheme.dimensionsPaddingExtended.page.normal),
                text = it,
                style = MaterialTheme.typographiesExtended.title.supra
            )
        }
    }

    @Composable
    private fun ColumnScope.contentBody(
        action: PageHelpAndServiceAction,
        helpAndServices: SectionSimpleTile.Data?,
        contacts: SectionSimpleRow.Data?,
        notices: SectionSimpleRow.Data?,
    ) {
        helpAndServices?.let {
            SectionSimpleTile(
                data = it,
                style = PageHelpAndServiceTheme.styles.sectionCard,
                onClick = action::onClickHelpAndServices
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.block.huge.vertical))
        contacts?.let {
            SectionSimpleRow(
                data = it,
                style = PageHelpAndServiceTheme.styles.sectionRow,
                onClick = action::onClickContacts
            )
        }
        notices?.let {
            SectionSimpleRow(
                data = it,
                style = PageHelpAndServiceTheme.styles.sectionRow,
                onClick = action::onClickNotices
            )
        }

    }

}