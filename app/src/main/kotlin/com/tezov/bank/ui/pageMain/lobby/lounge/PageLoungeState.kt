/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.pageMain.lobby.lounge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.tezov.bank.R
import com.tezov.bank.ui.pageMain.lobby.help_and_service.PageHelpAndServiceState
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageState


class PageLoungeState private constructor(
    val animation: PageLoungeAnimation,
    val nameState: State<String>,
    val iconState: State<Int>,
) : PageState {

    var header: Header? = null

    companion object {

        @Composable
        fun create(
            animationState: PageLoungeAnimation = PageLoungeAnimation.remember(),
            nameState: State<String> = mutableStateOf("M.TEZOV"),
            iconState: State<Int> = mutableStateOf(R.drawable.img_suitcase_blue),
        ) = PageLoungeState(
            animation = animationState,
            nameState = nameState,
            iconState = iconState,
        )
    }


    data class Header(
        val title: String? = null,
        val headline: String? = null,
        val body: String? = null,
        val label: String? = null,
        val note: String? = null,
    )

    init {
        header = Header(
            title = "Mobile comme vous!",
            headline = "N°1",
            body = "Banque n°1 de la relation client digitale",
            label = "depuis 3 ans",
            note = "version 3.34.0",
        )

    }

}