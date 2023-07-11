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

import com.tezov.lib_core_kotlin.async.notifier.observable.ObservableValue
import com.tezov.lib_core_kotlin.async.notifier.observer.Observer

abstract class ObserverValue<VALUE : Any?>(owner: Any) :
    Observer<Nothing, ObservableValue.Packet<VALUE>>(owner, null) {

    final override fun onChanged(packet: ObservableValue.Packet<VALUE>) {
        if (isCanceled) {
            onCancel()
        } else {
            onComplete(packet.value)
        }
    }

    open fun onCancel() {
        throw NotImplementedError()
    }

    open fun onComplete(value: VALUE) {
        throw NotImplementedError()
    }

}