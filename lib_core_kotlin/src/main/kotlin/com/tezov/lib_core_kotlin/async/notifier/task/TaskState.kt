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


class TaskState : TaskValue<Nothing?>() {

    fun notifyComplete() {
        super.notifyComplete(value = null)
    }

    fun notifyException(exception: Throwable) {
        super.notifyException(value = null, exception = exception)
    }

    fun notifyException(message: String) {
        super.notifyException(value = null, message = message)
    }


    companion object {

        fun Complete() = TaskState().also {
            it.notifyComplete()
        }.obtainScope()

        fun Exception(exception: Throwable) = TaskState().also {
            it.notifyException(exception)
        }.obtainScope()

        fun Exception(message: String) = TaskState().also {
            it.notifyException(message)
        }.obtainScope()

        fun Canceled() = TaskState().also {
            it.cancel()
            it.notifyCanceled()
        }.obtainScope()

    }

}