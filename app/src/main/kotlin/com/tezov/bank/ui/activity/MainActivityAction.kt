/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.activity

import androidx.compose.runtime.Composable
import com.tezov.bank.navigation.NavigationRoutes
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.SnackbarAction
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.ActivityAction

class MainActivityAction private constructor(
    val navigationRoutes: NavigationRoutes,
) : ActivityAction<MainActivityState> {

    companion object {
        @Composable
        fun create(
            navigationController: com.tezov.lib_core_android_kotlin.navigation.NavigationController,
            snackbarAction: SnackbarAction,
        ): MainActivityAction {
            return MainActivityAction(
                navigationRoutes = NavigationRoutes(
                    controller = navigationController,
                    snackbarAction = snackbarAction,
                ),
            )
        }
    }

}