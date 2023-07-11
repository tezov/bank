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
package com.tezov.lib_core_kotlin.async.notifier.task

import com.tezov.lib_core_kotlin.type.primaire.Pair
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNotNull

class TaskValuePair<F : Any, S : Any> : TaskValue<Pair<F?, S?>?>() {

    companion object {

        fun <F : Any, S : Any> Complete(first: F?, second: S?) = TaskValuePair<F, S>().also {
            it.notifyComplete(Pair(first, second))
        }.obtainScope()

        fun <F : Any, S : Any> Exception(
            first: F? = null,
            second: S? = null,
            exception: Throwable
        ) = TaskValuePair<F, S>().also {
            if(first.isNotNull ||second.isNotNull){
                it.notifyException(Pair(first, second), exception)
            }
            else {
                it.notifyException(null, exception)
            }
        }.obtainScope()

        fun <F : Any, S : Any> Exception(first: F? = null, second: S? = null, message: String) =
            TaskValuePair<F, S>().also {
                if(first.isNotNull ||second.isNotNull){
                    it.notifyException(Pair(first, second), message)
                }
                else {
                    it.notifyException(null, message)
                }
            }.obtainScope()

        fun <F : Any, S : Any> Canceled() = TaskValuePair<F, S>().also {
            it.cancel()
            it.notifyCanceled()
        }.obtainScope()

    }


}