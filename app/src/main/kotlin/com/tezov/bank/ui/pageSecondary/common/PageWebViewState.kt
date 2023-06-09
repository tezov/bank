/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.pageSecondary.common

import androidx.compose.runtime.Composable
import com.tezov.bank.application.LocalViewModelManager
import com.tezov.bank.navigation.NavigationRoutes.Route.WebView
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageState
import com.tezov.lib_core_kotlin.type.collection.ListEntry

class PageWebViewState private constructor() : PageState {

    var placeholders: ListEntry<String, String>? = null

    companion object {

        @Composable
        fun create(route: NavigationRouteManager.Route): PageWebViewState {
            val webView = (route as? WebView) ?: kotlin.run {
                throw IllegalStateException("route ${route::class.simpleName} is not the expected type ${WebView::class.simpleName}")
            }
            return PageWebViewState().also {
                val data = LocalViewModelManager.retrieveData(it, webView.url)
                it.decode(data)
            }
        }

        const val PLACEHOLDER_PAGE_NAME = "page_name"

    }

    private fun decode(data: String) {
        placeholders = ListEntry<String, String>().apply {
            add(PLACEHOLDER_PAGE_NAME, data)
        }
    }

}