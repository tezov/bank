

package com.tezov.bank.ui.pageSecondary.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tezov.app.R
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.lib_adr_app_core.navigation.navigator.ComposableNavigator
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Icon
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Shadow
import com.tezov.lib_adr_ui_cpt.component.core.chunk.WebViewRawResource
import com.tezov.lib_adr_app_core.ui.compositionTree.page.Page
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_kmm_core.type.collection.ListEntry
import com.tezov.lib_adr_ui_cpt.component.core.chunk.WebView
import com.tezov.lib_adr_ui_core.extension.ExtensionCompositionLocal
import com.tezov.lib_adr_ui_core.theme.style.padding
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsCommonExtended
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsIconExtended
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_adr_ui_core.type.primaire.size

object PageWebView : Page<PageWebViewState, PageWebViewAction> {

    @Composable
    override fun Page<PageWebViewState, PageWebViewAction>.content(graphEntry: ComposableNavigator.GraphEntry, innerPadding: PaddingValues) {
        val accessor = DiAccessorAppUiPage(requester = this).contextWebView().apply {
            remember()
        }
        val state = accessor.state()
        val action = accessor.action()
        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PageWebViewTheme provides PageWebViewTheme.provideColors(),
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PageWebViewTheme.colors.backgroundBody)
            ) {
                contentHeader(action = action)
                contentBody(rawResourceId = state.resourceId, placeholders = state.placeholders)
            }
        }

    }

    @Composable
    private fun ColumnScope.contentHeader(
        action: PageWebViewAction,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(PageWebViewTheme.colors.backgroundHeader),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon.Clickable(
                    radiusRipple = MaterialTheme.dimensionsIconExtended.modal.normal.radiusMin,
                    colorRipple = PageWebViewTheme.colors.accent,
                    onClick = action::onClickClose
                ) {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.dimensionsIconExtended.modal.normal),
                        painter = painterResource(id = com.tezov.lib_adr_ui_cpt.R.drawable.ic_close_24dp),
                        contentDescription = stringResource(id = R.string.dlg_login_auth_icon_close),
                        tint = PageWebViewTheme.colors.accent,
                    )
                }
            }
            Shadow.Line(
                elevation = (MaterialTheme.dimensionsCommonExtended.elevation.normal),
            )
        }
    }

    @Composable
    private fun ColumnScope.contentBody(
        rawResourceId: Int?,
        placeholders:ListEntry<String, String>?
    ) {
        rawResourceId ?: return
        WebView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f),
            rawHtmlResourceId = rawResourceId,
            placeholders = placeholders,
            onUnavailable = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.dimensionsPaddingExtended.block.normal)
                ) {
                    //hack: webview crash if missing -> find another solution
                    Text(text = "Webview not installed on your device, can not render the view")
                }
            }
        )

    }

}