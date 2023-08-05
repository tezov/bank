

package com.tezov.bank.ui.pageMain.lobby.splash

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tezov.app.R
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.lib_adr_sdk_core.navigation.navigator.GraphEntry
import com.tezov.lib_adr_sdk_core.ui.component.chunk.WebViewRawResource
import com.tezov.lib_adr_sdk_core.ui.compositionTree.page.Page
import com.tezov.lib_adr_sdk_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_adr_sdk_core.ui.theme.theme.colorsExtended

object PageSplash : Page<PageSplashState, PageSplashAction> {

    @Composable
    override fun Page<PageSplashState, PageSplashAction>.content(
        graphEntry: GraphEntry,
        innerPadding: PaddingValues
    ) {
        val accessor = DiAccessorAppUiPage(requester = this).contextSplash().apply {
            remember()
        }
        val action = accessor.action()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorsExtended.background.default
        ) {
            WebViewRawResource(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                rawHtmlResourceId = R.raw.html_terms
            ) {
                if (it == "onStart") {
                    action.onStart()
                    true
                } else {
                    false
                }
            }
        }
    }

}