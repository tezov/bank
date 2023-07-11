/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation.bottom_navigation

import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager.Route
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySubAction

class BottomNavigationAction private constructor(val navigationController: NavigationController) :
    ActivitySubAction<BottomNavigationState> {

    companion object {
        @Composable
        fun create(
            navigationController: NavigationController,
        ) = BottomNavigationAction(
            navigationController = navigationController,
        )
    }

    fun onClickItem(route: Route) {
        navigationController.requestNavigate(route, this)
    }


}