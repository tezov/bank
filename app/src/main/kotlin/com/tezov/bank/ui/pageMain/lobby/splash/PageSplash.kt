

package com.tezov.bank.ui.pageMain.lobby.splash

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.tezov.bank.R
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry
import com.tezov.lib_core_android_kotlin.ui.component.chunk.WebViewRawResource
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.theme.theme.colorsExtended
import kotlinx.coroutines.delay

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