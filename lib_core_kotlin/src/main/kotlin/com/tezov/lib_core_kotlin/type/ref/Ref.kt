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
package com.tezov.lib_core_kotlin.type.ref

import com.tezov.lib_core_kotlin.type.ref.Ref.Companion.value
import com.tezov.lib_core_kotlin.util.UtilsNull.ifNotNull
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNotNull
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNull
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

abstract class Ref<T : Any> protected constructor() {
    var info: RefInfo<T>? = null
        private set

    protected fun updateInfo(referent: T?) {
        info = referent?.let {
            RefInfo(referent)
        }
    }

    val type get() = info?.type

    override fun hashCode() = info?.hashCode() ?: 0

    override fun equals(other: Any?) =
        if (other is Ref<*>) {
            if (other.value.isNotNull && value.isNotNull) {
                other.hashCode() == hashCode()
            } else {
                other.value.isNull && value.isNull
            }
        } else {
            if (other.isNotNull && value.isNotNull) {
                this.hashCode() == other.hashCode()
            } else {
                other.isNull && value.isNull
            }
        }

    abstract var value:T?

    inner class LostRef<T : Any>(t: T, q: ReferenceQueue<T>?) : WeakReference<T>(t, q) {

        override fun hashCode() = this@Ref.hashCode()

        override fun equals(other: Any?) = info.hashCode() == other.hashCode()
    }

    companion object {

        val <T : Any> Ref<T>?.value get() = this?.value

        val <T : Any> Ref<T>?.type get() = this?.type

    }
}