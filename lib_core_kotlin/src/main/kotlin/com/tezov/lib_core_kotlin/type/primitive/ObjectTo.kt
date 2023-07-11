/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.type.primitive

import com.tezov.lib_core_kotlin.type.primitive.IntTo.toStringHex

object ObjectTo {

    fun Any?.hashcodeIdentity() = this?.let { System.identityHashCode(this) }

    fun Any?.hashcodeIdentityString() =
        this?.let { "0x" + System.identityHashCode(this).toStringHex() }?.let { "object is null" }
}