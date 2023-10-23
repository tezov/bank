

package com.tezov.bank.ui.pageMain.lobby.loginAuth

import android.util.Log
import com.tezov.bank.navigation.NavigationRouteManager.Route
import com.tezov.bank.navigation.NavigationUrl
import com.tezov.bank.ui.dialog.lobby.loginForgotten.DialogLoginForgotten
import com.tezov.lib_adr_app_core.navigation.NavigationController
import com.tezov.lib_adr_app_core.ui.composition.activity.sub.dialog.DialogAction
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageAction
import com.tezov.lib_adr_app_core.ui.di.module.ModuleCoreUiActivity
import com.tezov.lib_kmm_core.async.notifier.Event
import kotlinx.coroutines.CoroutineScope

class PageLoginAuthAction private constructor(
    private val coroutineScope: CoroutineScope,
    private val navigationController: NavigationController,
    private val dialogAction: DialogAction,
) : PageAction<PageLoginAuthState>() {

    companion object {

        fun create(
            coroutineScope: CoroutineScope,
            navigationController: NavigationController,
            dialogAction: DialogAction
        ) = PageLoginAuthAction(
            coroutineScope = coroutineScope,
            navigationController = navigationController,
            dialogAction = dialogAction,
        )
    }

    fun onClickClose() {
        navigationController.requestNavigateBack(this)
    }

    fun onClickConnect() {
        navigationController.requestNavigate(Route.NavAuth, this)
    }

    fun onClickLoginForgotten() {
        //TODO how to unregister on multiple click ??? Before unregister all with notifier
        dialogAction.collectNavigateOnce(coroutineScope) {
            if(it == Event.Confirm) { requestLoginRecovery() }
        }
        dialogAction.showOnCardWithOverlay(this) {
            DialogLoginForgotten()
        }
    }

    private fun requestLoginRecovery() {
        navigationController.requestNavigate(Route.WebView(url = NavigationUrl.HTTPS_LOGIN_FORGOTTEN), this)
    }

    fun onClickPasswordForgotten() {
        navigationController.requestNavigate(Route.WebView(url = NavigationUrl.HTTPS_PASSWORD_FORGOTTEN), this)
    }

}