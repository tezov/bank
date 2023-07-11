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

import kotlin.reflect.KClass

class RefInfo<T : Any>(ref: T) {
    val type: KClass<out T> = ref::class
    private val hashcode: Int = ref.hashCode()

    fun isTypeEqual(other: Any) = type == other::class

    fun isTypeEqual(type: KClass<out T>) = this.type == type

    override fun hashCode() = hashcode

    override fun equals(other: Any?) = other?.let {
        if (other is RefInfo<*>) {
            hashcode == other.hashcode
        } else {
            hashcode == other.hashCode()
        }
    } ?: false

}