

package com.tezov.bank.viewModel

import androidx.compose.runtime.Composable
import com.tezov.app.R
import com.tezov.bank.navigation.NavigationRouteManager.Route.Terms
import com.tezov.bank.navigation.NavigationRouteManager.Route.WebView
import com.tezov.bank.navigation.NavigationUrl
import com.tezov.lib_adr_app_core.navigation.NavigationController
import com.tezov.lib_adr_app_core.navigation.NavigationNotifier
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager
import com.tezov.lib_adr_app_core.ui.compositionTree.activity.Activity
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageState
import com.tezov.lib_adr_app_core.ui.di.accessor.DiAccessorCoreUiActivity
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_kmm_core.extension.ExtensionBoolean.isTrue
import kotlinx.coroutines.CoroutineScope

//TODO, ViewModel complet async + adapter la navigation en async pour gérer l'attente réponse avant ouverture page.
class ViewModelManager private constructor(
    coroutineScope: CoroutineScope,
    navigationNotifier: NavigationNotifier,
) {

    companion object{

        @Composable
        operator fun invoke():ViewModelManager{
            val accessor = DiAccessorCoreUiActivity().with(Activity.LocalActivity.current)
            val state = accessor.contextCore().state()
            val action = accessor.contextCore().action()
            return ViewModelManager(
                coroutineScope = state.coroutineScope,
                navigationNotifier = action.navigationNotifier
            )
        }

    }

    init {

        navigationNotifier.collectNavigate(coroutineScope) { value ->
            if( value.option?.clearStack.isTrue){
                value.from?.let { releaseDataUntil(it, false) }
            }
            value.option?.popUpTo?.let {
                releaseDataUntil(it.route, it.inclusive)
            }
            when(value.to) {
                is NavigationRouteManager.Route.Back -> {
                    value.from?.let { releaseData(it) }
                }
                is NavigationRouteManager.Route.Finish -> {
                    releaseSession()
                }
                else -> {
                    requestData(value.to)
                }
            }
        }

    }

    private fun releaseSession(){

    }

    private fun releaseDataUntil(from: NavigationRouteManager.Route, inclusive:Boolean){

    }

    private fun releaseData(from: NavigationRouteManager.Route){
        when(from){
            is WebView ->{
                dataProvider.remove(from.url)
            }
            is Terms ->{
                dataProvider.remove(from.url)
            }
        }
    }

    private fun requestData(to: NavigationRouteManager.Route){
        when(to){
            is WebView ->{
                when(to.url){
                    NavigationUrl.HTTPS_LOGIN_FORGOTTEN -> {
                        dataProvider[to.url] = "Récupération Login"
                    }
                    NavigationUrl.HTTPS_PASSWORD_FORGOTTEN -> {
                        dataProvider[to.url] = "Récupération Mot de passe"
                    }
                    else -> {}
                }
            }
            is Terms -> {
                dataProvider[to.url] = R.raw.html_terms
            }
        }
    }


    private val dataProvider = mutableMapOf<String, Any>()

    fun retrieveData(page: PageState, key:String) = dataProvider[key]?: "No Data Found"

}