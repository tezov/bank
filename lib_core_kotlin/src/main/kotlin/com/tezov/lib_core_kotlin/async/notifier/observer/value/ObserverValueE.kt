/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

/*
 * ********************************************************************************
 * Created by Tezov under MIT LICENCE.
 * For any request, please send me an email to tezov.app@gmail.com.
 * I'll be glad to answer you if your request is sane :)
 * ********************************************************************************
 */
package com.tezov.lib_core_kotlin.async.notifier.observer.value

import com.tezov.lib_core_kotlin.async.notifier.observable.ObservableValueE
import com.tezov.lib_core_kotlin.async.notifier.observer.Observer

abstract class ObserverValueE<VALUE:Any?>(owner: Any) :
    Observer<Nothing, ObservableValueE.Packet<VALUE>>(owner, null) {

    final override fun onChanged(packet: ObservableValueE.Packet<VALUE>) {
        if (isCanceled) {
            onCancel()
        } else {
            packet.exception?.let {
                onException(packet.value, it)
            } ?: kotlin.run {
                onComplete(packet.value)
            }
        }
    }

    open fun onComplete(value: VALUE) {
        throw NotImplementedError()
    }

    open fun onException(value: VALUE?, exception: Throwable?) {
        throw NotImplementedError()
    }

    open fun onCancel() {
        throw NotImplementedError()
    }

}