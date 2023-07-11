/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation

import com.tezov.lib_core_kotlin.async.notifier.Notifier
import com.tezov.lib_core_kotlin.async.notifier.observable.ObservableEvent
import com.tezov.lib_core_kotlin.async.notifier.observable.ObservableValue
import com.tezov.lib_core_kotlin.async.notifier.observer.event.ObserverEvent
import com.tezov.lib_core_kotlin.async.notifier.observer.value.ObserverValue
import com.tezov.lib_core_kotlin.extension.ExtensionBoolean.isTrue

class NavigationNotifier {

    private val notifier = Notifier(ObservableValue<NavigationController.Request>(), false)

    //TODO SUGAR SYNTAXE
    fun register(observer: ObserverValue<NavigationController.Request>) =  notifier.register(observer)

    fun unregister(observer: ObserverValue<NavigationController.Request>){
        notifier.unregister(observer)
    }

    fun unregisterAll(owner: Any){
        notifier.unregisterAll(owner)
    }

    internal fun onNavigate(request: NavigationController.Request) {
        if(request.from == request.to && request.option?.singleTop.isTrue){
            return
        }
        notifier.obtainPacket().also {
            it.value = request
        }
    }

}