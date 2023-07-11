/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation.navigator.composableOverlay

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.navigation.*
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation
import com.tezov.lib_core_android_kotlin.navigation.NavHost
import com.tezov.lib_core_android_kotlin.navigation.navigator.ComposableNavigator
import com.tezov.lib_core_android_kotlin.navigation.navigator.ComposableNavigator.Companion.content
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNotNull

@Navigator.Name("navigator_composable_overlay")
internal class ComposableOverlayNavigator : ComposableNavigator() {

    @Composable
    override fun composePrepare(
        navHost: NavHost,
        entry: NavBackStackEntry,
    ) {
        if (navHost.isNavigatingBack) return
        val entryContent = entry.content
        if (entryContent.backgroundId.isNotNull) return
        val previousEntry = navHost.previousEntryOf(entry) ?: return
        val previousEntryContent = previousEntry.content
        previousEntryContent.foregroundId = entry.id
        entryContent.backgroundId = previousEntry.id
    }

    override fun createDestination(): Content {
        return Content(this) { }
    }

    @NavDestination.ClassType(Composable::class)
    class Content(
        navigator: ComposableOverlayNavigator,
        animationConfig: NavigationAnimation.Config? = null,
        content: @Composable BoxScope.(GraphEntry) -> Unit
    ) : ComposableNavigator.Content(navigator, animationConfig, content)

}
