

package com.tezov.bank.ui.pageMain.lobby.help_and_service

import com.tezov.app.R
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleRow
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleTile
import com.tezov.lib_adr_ui_cpt.component.menu.element.SimpleRow
import com.tezov.lib_adr_ui_cpt.component.menu.element.SimpleTile
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageState

class PageHelpAndServiceState private constructor() : PageState {
    var header: Header? = null
    var helpAndServices: SectionSimpleTile.Data? = null
    var contacts: SectionSimpleRow.Data? = null
    var notices: SectionSimpleRow.Data? = null

    companion object {

        fun create() = PageHelpAndServiceState()
    }

    data class Header(
        val headline: String? = null,
    )

    init {
        header = Header(
            headline = "Aide & Service"
        )
        helpAndServices = SectionSimpleTile.Data(
            template = SimpleTile.Template.IconTopEnd,
            tile = listOf(
                SimpleTile.Data(
                    title = "Opposer une carte",
                    iconInfoId = R.drawable.ic_crisis_24dp
                ),
                SimpleTile.Data(
                    title = "Contester un prélèvement",
                    iconInfoId = R.drawable.ic_argue_24dp
                ),
                SimpleTile.Data(
                    title = "Suivre mon dossier",
                    iconInfoId = R.drawable.ic_checklist_24dp
                ),
                SimpleTile.Data(
                    title = "Trouver un distributeur",
                    iconInfoId = R.drawable.ic_atm_24dp
                ),
                SimpleTile.Data(
                    title = "Retirer à l'étranger",
                    iconInfoId = R.drawable.ic_explore_24dp
                ),
                SimpleTile.Data(
                    title = "Découvrir l'application",
                    iconInfoId = com.tezov.lib_adr_ui_cpt.R.drawable.ic_search_24dp
                ),
                SimpleTile.Data(
                    title = "Accéder à l'assitance technique",
                    iconInfoId = com.tezov.lib_adr_ui_cpt.R.drawable.ic_help_24dp
                ),
            )
        )

        contacts = SectionSimpleRow.Data(
            title = "CONTACTER LA HELLO TEAM",
            rows = listOf(
                SimpleRow.Data(
                    title = "Appeler",
                    iconInfoId = com.tezov.lib_adr_ui_cpt.R.drawable.ic_call_24dp
                ),
                SimpleRow.Data(
                    title = "Service sourds et malentendats",
                    iconInfoId = com.tezov.lib_adr_ui_cpt.R.drawable.ic_hearing_disabled_24dp
                ),
            )
        )

        notices = SectionSimpleRow.Data(
            title = "MENTION LEGALES",
            rows = listOf(
                SimpleRow.Data(title = "Mentions légales"),
                SimpleRow.Data(title = "Mentions légales Bourse"),
                SimpleRow.Data(title = "Politique des cookies"),
                SimpleRow.Data(title = "Paramètres des cookies"),
                SimpleRow.Data(title = "A propos de l'accessibilité"),
            )
        )

    }


}