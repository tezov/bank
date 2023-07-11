/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.pageMain.auth.payment

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.tezov.bank.R
import com.tezov.bank.ui.component.block.SectionSimpleTile
import com.tezov.bank.ui.component.element.SimpleTile
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageState

class PagePaymentState private constructor() : PageState {

    var header: Header? = null
    var cardsSmall: SectionSimpleTile.Data? = null
    var cardsLarge: SectionSimpleTile.Data? = null

    companion object {

        fun create() = PagePaymentState()
    }

    data class Header(
        val headline: String? = null,
    )

    init {

        header = Header(
            headline = "Paiments"
        )

        cardsSmall = SectionSimpleTile.Data(
            template = SimpleTile.Template.IconTopEnd,
            tile = listOf(
                SimpleTile.Data(
                    title = "Partager\nmon RIB",
                    iconInfoId = R.drawable.img_rib_share
                ),
                SimpleTile.Data(
                    title = "Faire\nun virement",
                    iconInfoId = R.drawable.img_transfer_money
                ),
                SimpleTile.Data(
                    title = "Gérer\nmes chèques",
                    iconInfoId = R.drawable.img_cheque_manage
                ),
                SimpleTile.Data(
                    title = "Gérer\nmes cartes",
                    iconInfoId = R.drawable.img_card_manage
                ),
            )
        )

        cardsLarge = SectionSimpleTile.Data(
            template = SimpleTile.Template.IconEnd,
            tile = listOf(
                SimpleTile.Data(
                    title = "Lyf Pay",
                    subtitle = "Payer avec votre mobile.",
                    iconInfoId = R.drawable.img_lyf,
                    iconInfoColor = Color.Red
                ),
                SimpleTile.Data(
                    title = "Paylib",
                    subtitle = "Envoyer de l'argent vers un mobile",
                    iconInfoId = R.drawable.img_paylib,
                    iconInfoColor = Color.DarkGray
                ),
                SimpleTile.Data(
                    title = "PaypPal",
                    subtitle = "Payer en ligne",
                    iconInfoId = R.drawable.img_paypal,
                    iconInfoColor = Color.Blue
                ),
            )
        )

    }

}