/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.application

import androidx.compose.runtime.Composable
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiActivity
import com.tezov.bank.viewModel.ViewModelManager
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalCoreApplication
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

val LocalApplication @Composable get() = LocalCoreApplication.current as Application

val LocalViewModelManager @Composable get() = LocalApplication.viewModelManager ?: throw IllegalStateException("Application compose not initialized")

class Application : com.tezov.lib_core_android_kotlin.application.Application() {
    lateinit var accessorAppUi: DiAccessorAppUiActivity
    internal var viewModelManager: ViewModelManager? = null
        private set

    override fun onCreate() {
        super.onCreate()
        accessorAppUi = DiAccessorAppUiActivity::class.primaryConstructor?.apply {
            isAccessible = true
        }?.call() ?: throw Exception("failed to create instance of AccessorAppUiApplication")
    }

    @Composable
    fun composeInit(){
        viewModelManager = ViewModelManager()
    }

}