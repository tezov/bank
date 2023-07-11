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
import androidx.navigation.*
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation
import com.tezov.lib_core_android_kotlin.navigation.navigator.ComposableNavigator
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry

@Navigator.Name("navigator_composable_transient")
internal class ComposableTransientNavigator : ComposableNavigator() {

    override fun createDestination(): Content {
        return Content(this) { }
    }

    @NavDestination.ClassType(Composable::class)
    class Content(
        navigator: ComposableNavigator,
        animationConfig: NavigationAnimation.Config? = null,
        content: @Composable BoxScope.(GraphEntry) -> Unit
    ) : ComposableNavigator.Content(navigator, animationConfig, content)


}
