/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation.top_app_bar

import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager.Route
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySubAction

class TopAppBarAction private constructor(val navigationController: NavigationController) :
    ActivitySubAction<TopAppBarState> {

    companion object {
        @Composable
        fun create(
            navigationController: NavigationController,
        ) = TopAppBarAction(
            navigationController = navigationController,
        )
    }

    fun onClickIconButton(route: Route) {
        navigationController.requestNavigate(route, this)
    }


}