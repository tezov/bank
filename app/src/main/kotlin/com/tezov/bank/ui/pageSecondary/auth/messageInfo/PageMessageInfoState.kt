/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.pageSecondary.auth.messageInfo

import androidx.compose.runtime.Composable
import com.tezov.bank.ui.component.block.SectionMessageRow
import com.tezov.bank.ui.component.element.MessageRow
import com.tezov.lib_core_android_kotlin.ui.component.block.HorizontalPager
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageState

class PageMessageInfoState private constructor() : PageState {

    var header: Header? = null
    var messages: SectionMessageRow.Data? = null

    companion object {
        @Composable
        fun create() = PageMessageInfoState()
    }

    data class Header(
        val tabNotification: HorizontalPager.WithTabRow.Tab? = null,
        val tabMessageBox: HorizontalPager.WithTabRow.Tab? = null,
    )

    init {

        header = Header(
            tabNotification = HorizontalPager.WithTabRow.Tab("Notifications"),
            tabMessageBox = HorizontalPager.WithTabRow.Tab("Messagerie"),
        )

        messages = SectionMessageRow.Data(
            rows = listOf(
                MessageRow.Data(
                    title = "Opératon refusée",
                    subtitle = "Samedi 22 avril",
                    active = true
                ),
                MessageRow.Data(
                    title = "Vos avantages Premier printemps-été 2023",
                    subtitle = "Lundi 17 avril",
                    active = false
                ),
                MessageRow.Data(
                    title = "Evolutions tarifaires au 7 juin 2023",
                    subtitle = "Lundi 03 avril",
                    active = false
                ),
                MessageRow.Data(
                    title = "Virements: nouvelle liste de bénéficiaires",
                    subtitle = "Mardi 21 mars",
                    active = true
                ),
                MessageRow.Data(
                    title = "Sécurité: Spoofing, la fraude par téléphone",
                    subtitle = "Lundi 06 février",
                    active = true
                ),
                MessageRow.Data(
                    title = "Fonds de Garantie des Dépots et de Résolution",
                    subtitle = "Mercredi 18 janvier",
                    active = true
                ),
                MessageRow.Data(
                    title = "Modification de votre convention de compte",
                    subtitle = "Mercredi 18 janvier",
                    active = true
                ),
                MessageRow.Data(
                    title = "Virements: nouvelle liste de bénéficiaires",
                    subtitle = "Samedi 14 janvier",
                    active = false
                ),
                MessageRow.Data(
                    title = "Opératon refusée",
                    subtitle = "Lundi 06 janvier",
                    active = false
                ),
                MessageRow.Data(
                    title = "Virement: suppression d'un bénéficiaire",
                    subtitle = "Mercredi 16 Novembre",
                    active = false
                ),
                MessageRow.Data(
                    title = "Evolutions tarifaires au 01/01/2023",
                    subtitle = "Lundi 31 Octobre",
                    active = false
                ),
                MessageRow.Data(
                    title = "Plafond paiement atteint à 80%",
                    subtitle = "Vendredi 14 Octobre",
                    active = false
                ),
            )
        )


    }

}