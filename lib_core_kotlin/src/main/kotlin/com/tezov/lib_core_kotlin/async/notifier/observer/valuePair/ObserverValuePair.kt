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
package com.tezov.lib_core_kotlin.async.notifier.observer.valuePair

import com.tezov.lib_core_kotlin.async.notifier.observer.value.ObserverValue
import com.tezov.lib_core_kotlin.type.primaire.Pair

abstract class ObserverValuePair<F : Any, S : Any>(owner: Any) :
    ObserverValue<Pair<F?, S?>?>(owner) {

    final override fun onComplete(value: Pair<F?, S?>?) {
        value?.let {
            onComplete(value.first, value.second)
        } ?: kotlin.run {
            onComplete(null, null)
        }
    }

    open fun onComplete(first: F?, second: S?) {
        throw NotImplementedError()
    }

}