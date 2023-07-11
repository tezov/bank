/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.composition.activity

import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.NavigationNotifier
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.ActivityAction
import kotlinx.coroutines.CoroutineScope

class ActivityCoreAction private constructor(
    val navigationController: NavigationController,
    val navigationNotifier: NavigationNotifier,
) : ActivityAction<ActivityCoreState> {

    companion object {

        fun create(
            navigationController: NavigationController,
            navigationNotifier: NavigationNotifier,
        ) = ActivityCoreAction(
            navigationController = navigationController,
            navigationNotifier = navigationNotifier,
        )
    }


}