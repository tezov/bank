

package com.tezov.bank.ui.pageSecondary.common

import androidx.compose.runtime.Composable
import com.tezov.app.R
import com.tezov.bank.application.LocalViewModelManager
import com.tezov.bank.navigation.NavigationRouteManager.Route.WebView
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageState
import com.tezov.lib_kmm_core.type.collection.ListEntry

class PageWebViewState private constructor() : PageState() {

    var resourceId:Int? = null
    var placeholders: ListEntry<String, String>? = null

    companion object {

        @Composable
        fun create(route: NavigationRouteManager.Route): PageWebViewState {
            val webView = (route as? WebView) ?: kotlin.run {
                throw IllegalStateException("route ${route::class.simpleName} is not the expected type ${WebView::class.simpleName}")
            }
            return PageWebViewState().also {
                val data = LocalViewModelManager.retrieveData(it, webView.url)
                when(data){
                    is String -> it.decode(data)
                    is Int -> it.decode(data)
                }
            }
        }

        const val PLACEHOLDER_PAGE_NAME = "page_name"

    }

    private fun decode(data: String) {
        resourceId = R.raw.html_not_implemented
        placeholders = ListEntry<String, String>().apply {
            add(PLACEHOLDER_PAGE_NAME, data)
        }
    }

    private fun decode(data: Int) {
        resourceId = data
        placeholders = null
    }

}