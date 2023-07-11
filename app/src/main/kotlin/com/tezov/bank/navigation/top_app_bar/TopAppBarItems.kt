/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.navigation.top_app_bar

import com.tezov.bank.R
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager.Route
import com.tezov.lib_core_android_kotlin.navigation.top_app_bar.TopAppBarItemData

sealed class TopAppBarItems(icon: Int, route: Route) : TopAppBarItemData(icon, route) {
    companion object {
        val items: Set<TopAppBarItemData> by lazy {
            setOf(
                Back,
            )
        }
    }

    object Back : TopAppBarItemData(R.drawable.ic_arrow_left_24dp, NavigationRouteManager.Back)


}