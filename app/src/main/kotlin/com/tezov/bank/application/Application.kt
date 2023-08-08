

package com.tezov.bank.application

import androidx.compose.runtime.Composable
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiActivity
import com.tezov.bank.viewModel.ViewModelManager
import com.tezov.lib_adr_app_core.ui.compositionTree.activity.Activity.Companion.LocalCoreApplication
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

val LocalApplication @Composable get() = LocalCoreApplication.current as Application

val LocalViewModelManager @Composable get() = LocalApplication.viewModelManager ?: throw IllegalStateException("Application compose not initialized")

class Application : com.tezov.lib_adr_app_core.application.Application() {
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