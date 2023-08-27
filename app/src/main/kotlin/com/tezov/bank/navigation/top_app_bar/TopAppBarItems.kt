

package com.tezov.bank.navigation.top_app_bar

import com.tezov.app.R
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager.Route
import com.tezov.lib_adr_app_core.navigation.top_app_bar.TopAppBarItemData

sealed class TopAppBarItems(icon: Int, route: Route) : TopAppBarItemData(icon, route) {
    companion object {
        val items: Set<TopAppBarItemData> by lazy {
            setOf(
                Back,
            )
        }
    }

    object Back : TopAppBarItemData(com.tezov.lib_adr_ui_cpt.R.drawable.ic_arrow_left_24dp, Route.Back())


}