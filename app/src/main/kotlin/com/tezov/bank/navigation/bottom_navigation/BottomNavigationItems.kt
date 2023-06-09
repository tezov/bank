/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.navigation.bottom_navigation

import com.tezov.bank.R
import com.tezov.bank.navigation.NavigationRoutes.*
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager
import com.tezov.lib_core_android_kotlin.navigation.bottom_navigation.BottomNavigationItemData

sealed class BottomNavigationItems(
    titleResourceId: Int,
    iconActive: Int,
    iconInactive: Int,
    route: NavigationRouteManager.Route
) :
    BottomNavigationItemData(titleResourceId, iconActive, iconInactive, route) {
    companion object {
        val items: Set<BottomNavigationItemData> by lazy {
            setOf(
                BottomNavigationItemData(
                    R.string.nav_btm_account,
                    R.drawable.ic_home_24dp,
                    R.drawable.ic_home_line_24dp,
                    Route.Account
                ),
                BottomNavigationItemData(
                    R.string.nav_btm_discover,
                    R.drawable.ic_list_square_24dp,
                    R.drawable.ic_list_24dp,
                    Route.Discover
                ),
                BottomNavigationItemData(
                    R.string.nav_btm_payment,
                    R.drawable.ic_transfert_24dp,
                    R.drawable.ic_transfert_round_24dp,
                    Route.Payment
                ),
                BottomNavigationItemData(
                    R.string.nav_btm_help,
                    R.drawable.ic_help_square_24dp,
                    R.drawable.ic_help_square_line_24dp,
                    Route.Help
                ),
                BottomNavigationItemData(
                    R.string.nav_btm_profile,
                    R.drawable.ic_profile_24dp,
                    R.drawable.ic_profile_line_24dp,
                    Route.Profile
                ),
            )
        }
    }

}