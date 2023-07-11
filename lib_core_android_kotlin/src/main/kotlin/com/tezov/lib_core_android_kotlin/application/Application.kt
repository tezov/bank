/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.application

import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiActivity
import com.tezov.lib_core_kotlin.application.Application
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible


abstract class Application : Application() {
    lateinit var accessorCoreUi: DiAccessorCoreUiActivity

    override fun onCreate() {
        super.onCreate()
        accessorCoreUi = DiAccessorCoreUiActivity::class.primaryConstructor?.apply {
            isAccessible = true
        }?.call() ?: throw Exception("failed to create instance of AccessorCoreUiActivity")
    }

}