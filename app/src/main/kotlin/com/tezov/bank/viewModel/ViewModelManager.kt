

package com.tezov.bank.viewModel

import androidx.compose.runtime.Composable
import com.tezov.bank.navigation.NavigationRoutes.Route.WebView
import com.tezov.bank.navigation.NavigationUrl
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.NavigationNotifier
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageState
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_kotlin.async.notifier.Notifier
import com.tezov.lib_core_kotlin.async.notifier.observer.value.ObserverValue
import com.tezov.lib_core_kotlin.extension.ExtensionBoolean.isTrue

//TODO, ViewModel complet async + adapter la navigation en async pour gérer l'attente réponse avant ouverture page.
class ViewModelManager private constructor(
    navigationNotifier: NavigationNotifier,
) {

    companion object{

        @Composable
        operator fun invoke():ViewModelManager{
            val accessor = DiAccessorCoreUiActivity().with(Activity.LocalActivity.current)
            val action = accessor.contextCore().action()
            return ViewModelManager(action.navigationNotifier)
        }

    }

    private val subscription: Notifier.Subscription = navigationNotifier.register(object :
        ObserverValue<NavigationController.Request>(this) {
        override fun onComplete(
            value: NavigationController.Request
        ) {
            if( value.option?.clearStack.isTrue){
                value.from?.let { releaseDataUntil(it, false) }
            }
            value.option?.popUpTo?.let {
                releaseDataUntil(it.route, it.inclusive)
            }
            when(value.to) {
                NavigationRouteManager.Back -> {
                    value.from?.let { releaseData(it) }
                }
                is NavigationRouteManager.Finish -> {
                    releaseSession()
                }
                else -> {
                    requestData(value.to)
                }
            }
        }
    })

    private fun releaseSession(){

    }

    private fun releaseDataUntil(from: NavigationRouteManager.Route, inclusive:Boolean){

    }

    private fun releaseData(from: NavigationRouteManager.Route){
        when(from){
            is WebView ->{
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
        }
    }


    private val dataProvider = mutableMapOf<String, String>()

    fun retrieveData(page: PageState, key:String) = dataProvider[key]?: "No Data Found"

}