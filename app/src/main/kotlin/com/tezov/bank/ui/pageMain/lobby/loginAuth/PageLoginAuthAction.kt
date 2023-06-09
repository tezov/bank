/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.pageMain.lobby.loginAuth

import com.tezov.bank.navigation.NavigationRoutes.Route
import com.tezov.bank.navigation.NavigationUrl
import com.tezov.bank.ui.dialog.lobby.loginForgotten.DialogLoginForgotten
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageAction
import com.tezov.lib_core_kotlin.async.notifier.Event
import com.tezov.lib_core_kotlin.async.notifier.observer.event.ObserverEventE

class PageLoginAuthAction private constructor(
    private val navigationController: NavigationController,
    private val dialogAction: DialogAction,
) : PageAction<PageLoginAuthState> {


    companion object {

        fun create(
            navigationController: NavigationController,
            dialogAction: DialogAction
        ) = PageLoginAuthAction(
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
        dialogAction.unregisterAll(this)
        dialogAction.register(object : ObserverEventE<Event, Any?>(this, Event.Confirm) {
            override fun onComplete(event: Event, value: Any?) {
                unsubscribe()
                requestLoginRecovery()
            }
        })
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