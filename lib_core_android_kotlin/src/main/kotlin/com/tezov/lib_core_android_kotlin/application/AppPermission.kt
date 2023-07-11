/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.application

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityBase
import com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityBase.RequestForPermission
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.ActivityAction
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.ActivityState
import com.tezov.lib_core_kotlin.type.collection.ListEntry

object AppPermission {

    fun isGranted(context: Context, permission: String) = ActivityCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED

    class Checker(internal val activity: ActivityBase<*, *>) {
        private val resultPermissions = ListEntry<String, Boolean>()
        private lateinit var requestForPermission: RequestForPermission

        fun add(permission: String) {
            resultPermissions.add(permission, isGranted(activity.applicationContext, permission))
        }

        fun add(permission: List<String>) {
            permission.forEach { add(it) }
        }

        fun check() = RequestForPermission.Response(resultPermissions)
        suspend fun response(): RequestForPermission.Response {
            val response = check()
            return if (response.isAllGranted()) {
                response
            } else {
                requestForPermission = RequestForPermission(activity).apply {
                    add(response.denied())
                }
                requestForPermission.response()
            }
        }

    }
}