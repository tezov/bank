

package com.tezov.bank.navigation.bottom_navigation

import com.tezov.app.R
import com.tezov.bank.navigation.NavigationRouteManager.*
import com.tezov.lib_adr_app_core.navigation.bottom_navigation.BottomNavigationItem

object BottomNavigationItems {
    val items: List<BottomNavigationItem> by lazy {
    listOf(
        BottomNavigationItem(
            R.string.nav_btm_account,
            com.tezov.lib_adr_ui_cpt.R.drawable.ic_home_fill_24dp,
            com.tezov.lib_adr_ui_cpt.R.drawable.ic_home_24dp,
            Route.Account
        ),
        BottomNavigationItem(
            R.string.nav_btm_discover,
            R.drawable.ic_list_square_24dp,
            R.drawable.ic_list_24dp,
            Route.Discover
        ),
        BottomNavigationItem(
            R.string.nav_btm_payment,
            R.drawable.ic_transfert_24dp,
            R.drawable.ic_transfert_round_24dp,
            Route.Payment
        ),
        BottomNavigationItem(
            R.string.nav_btm_help,
            com.tezov.lib_adr_ui_cpt.R.drawable.ic_help_square_fill_24dp,
            com.tezov.lib_adr_ui_cpt.R.drawable.ic_help_square_24dp,
            Route.Help
        ),
        BottomNavigationItem(
            R.string.nav_btm_profile,
            R.drawable.ic_profile_24dp,
            R.drawable.ic_profile_line_24dp,
            Route.Profile
        ),
    )
}

}