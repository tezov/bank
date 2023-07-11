/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation.navigator.composable

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.get
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry

fun NavGraphBuilder.composableTransient(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    animationConfig: NavigationAnimation.Config? = null,
    content: @Composable BoxScope.(GraphEntry) -> Unit
) {
    addDestination(
        ComposableTransientNavigator.Content(
            provider[ComposableTransientNavigator::class],
            animationConfig,
            content
        ).apply {
            this.route = route
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
            deepLinks.forEach { deepLink ->
                addDeepLink(deepLink)
            }
        }
    )
}