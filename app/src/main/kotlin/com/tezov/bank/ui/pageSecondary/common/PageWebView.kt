

package com.tezov.bank.ui.pageSecondary.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tezov.app.R
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Icon
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Shadow
import com.tezov.lib_core_android_kotlin.ui.component.chunk.WebViewRawResource
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_core_android_kotlin.ui.extension.ExtensionCompositionLocal
import com.tezov.lib_core_android_kotlin.ui.theme.theme.dimensionsCommonExtended
import com.tezov.lib_core_android_kotlin.ui.theme.theme.dimensionsIconExtended
import com.tezov.lib_core_android_kotlin.ui.type.primaire.size
import com.tezov.lib_core_kotlin.type.collection.ListEntry

object PageWebView : Page<PageWebViewState, PageWebViewAction> {

    @Composable
    override fun Page<PageWebViewState, PageWebViewAction>.content(graphEntry: GraphEntry, innerPadding: PaddingValues) {
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
                contentBody(placeholders = state.placeholders)
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
                        painter = painterResource(id = R.drawable.ic_close_24dp),
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
        placeholders:ListEntry<String, String>?
    ) {
        WebViewRawResource(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f),
            rawHtmlResourceId = R.raw.html_not_implemented,
            placeholders = placeholders
        )

    }

}