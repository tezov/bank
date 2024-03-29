

package com.tezov.bank.ui.pageMain.auth.account

import androidx.compose.ui.graphics.Color
import com.tezov.app.R
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleValueRow
import com.tezov.lib_adr_ui_cpt.component.animated.block.SummaryCardAnimated
import com.tezov.lib_adr_ui_cpt.component.menu.element.SimpleValueRow
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageState
import com.tezov.lib_adr_ui_core.theme.style.OutfitStateSemantic

class PageAccountState private constructor() : PageState() {
    var header: Header?=null
    var incomings: SectionSimpleValueRow.Data?=null
    var histories: List<SectionSimpleValueRow.Data>?=null

    companion object {

        fun create() = PageAccountState()
    }

    data class Header(
        val iconActionMessageId: Int?=null,
        val iconActionAccountId: Int?=null,
        val headline: String?=null,
        val accountSummary: SummaryCardAnimated.Data?=null,
    )

    init {
        header = Header(
            iconActionMessageId = R.drawable.ic_bell_24dp,
            iconActionAccountId = com.tezov.lib_adr_ui_cpt.R.drawable.ic_dashboard_fill_24dp,
            headline = "Hello !",
            accountSummary = SummaryCardAnimated.Data(
                iconInfoId = R.drawable.ic_chart_line_24dp,
                iconActionId = com.tezov.lib_adr_ui_cpt.R.drawable.ic_3dot_h_24dp,
                surtitle = "N° **** 3475",
                title = "Compte de chèques",
                subTitle = "Aujourd'hui",
                amount = "47 123,98 €",
                actions = listOf(
                    "Informations du compte",
                    "Faire un virement",
                    "Catégoriser des opérations",
                    "Rechercher une opération",
                    "Pointer des opérations",
                    "Afficher le RIB",
                    "Trier par..."
                )
            )
        )

        val semantic = OutfitStateSemantic(
            neutral = Color(0xAAB9BDBB),
            info = Color(0xAA304275),
            alert = Color(0xAA945D2B),
            success = Color(0xAA57AC81),
            error = Color(0xAA8D3F3F),
        )

        incomings =  SectionSimpleValueRow.Data(
            title = "ENREGISTREES",
            iconInfoId = com.tezov.lib_adr_ui_cpt.R.drawable.ic_question_24dp,
            rows = listOf(
                SimpleValueRow.Data(
                    iconInfoId = R.drawable.cat_clock_24dp,
                    iconInfoColor = semantic.neutral,
                    title = "Facture carte du 010223 k06794851",
                    amount = "-10.20 €",
                ),
                SimpleValueRow.Data(
                    iconInfoId = R.drawable.cat_clock_24dp,
                    iconInfoColor = semantic.neutral,
                    title = "Facture carte du total...",
                    amount = "-133.13 €",
                ),
                SimpleValueRow.Data(
                    iconInfoId = R.drawable.cat_clock_24dp,
                    iconInfoColor = semantic.neutral,
                    title = "Facture carte du 010223 auchan 134 5784",
                    amount = "-1.00 €",
                ),
            )
        )

        histories = listOf(
            SectionSimpleValueRow.Data(
                title = "VENDREDI 14 AVRIL",
                rows = listOf(
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_dining_24dp,
                        iconInfoColor = semantic.success,
                        title = "Paiements cb amazon du 12/04 a payli2441535 - CLASS",
                        subTitle = "Achats,shopping",
                        amount = "-14.69 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_bar_24dp,
                        iconInfoColor = semantic.info,
                        title = "Prelevement bouygues telecom du 13/04 - EMMETEUR",
                        subTitle = "Téléphone",
                        amount = "-9.95 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_drop_24dp,
                        iconInfoColor = semantic.info,
                        title = "Paiement cb auchan du 11/04 a Faches-Thumesnil",
                        subTitle = "Alimentation, supermarché",
                        amount = "-41.46 €",
                    ),
                )
            ),
            SectionSimpleValueRow.Data(
                title = "JEUDI 06 AVRIL",
                rows = listOf(
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_dining_24dp,
                        iconInfoColor = semantic.success,
                        title = "Remboursement cb amazon",
                        subTitle = "Remboursement",
                        amount = "26.99 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_shower_24dp,
                        iconInfoColor = semantic.alert,
                        title = "Paiements cb otacos ganbetta du 31/03 à paris 15",
                        subTitle = "Restaurants, bars",
                        amount = "-17.20 €",
                    ),
                )
            ),
            SectionSimpleValueRow.Data(
                title = "MERCREDI 08 MARS",
                rows = listOf(
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_rocket_24dp,
                        iconInfoColor = semantic.success,
                        title = "Prlv sepa edf clients par mdt/ mm 9760236677",
                        subTitle = "Prélèvement",
                        amount = "-65.00 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_shower_24dp,
                        iconInfoColor = semantic.alert,
                        title = "Virement vers payward ltd - Motif : AA...",
                        subTitle = "Virement émis",
                        amount = "-778.20 €",
                    ),
                )
            ),
            SectionSimpleValueRow.Data(
                title = "VENDREDI 24 FEVRIER",
                rows = listOf(
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_rocket_24dp,
                        iconInfoColor = semantic.error,
                        title = "Paiements cb leroy merlin du 23/02 à Lesquin - Cart",
                        subTitle = "Bricolage et jardinage",
                        amount = "-36.50 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_bar_24dp,
                        iconInfoColor = semantic.success,
                        title = "Online store, latex doll",
                        subTitle = "Ameublement",
                        amount = "-3778.20 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_bar_24dp,
                        iconInfoColor = semantic.info,
                        title = "Paiment cb maxicoffe nord",
                        subTitle = "Alimentation, supermarché",
                        amount = "-0.40 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_dining_24dp,
                        iconInfoColor = semantic.info,
                        title = "Paiment cb distri-flandre du 16/02 a Fache",
                        subTitle = "Carburant",
                        amount = "-77.94 €",
                    )
                )
            ),
            SectionSimpleValueRow.Data(
                title = "MARDI 24 JANVIER",
                rows = listOf(
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_dining_24dp,
                        iconInfoColor = semantic.neutral,
                        title = "Paiement cb peage sanef du 22/01 a senlis",
                        subTitle = "Péage",
                        amount = "-17.30 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_bar_24dp,
                        iconInfoColor = semantic.neutral,
                        title = "Paiement cb peage sanef du 20/01 a senlis",
                        subTitle = "Péage",
                        amount = "-17.30 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_shower_24dp,
                        iconInfoColor = semantic.success,
                        title = "Paiement cb timbre fiscal du 05/12 a Rennes",
                        subTitle = "Impôts et taxes - Autres",
                        amount = "-30.00 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_drop_24dp,
                        iconInfoColor = semantic.alert,
                        title = "Paiement cb web amende.gouv du 26/11 a Rennes",
                        subTitle = "Autres dépenses à catégoriser",
                        amount = "-30.00 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_rocket_24dp,
                        iconInfoColor = semantic.error,
                        title = "Paiement cb photomaton du 22/11 à Paris - Carte*88",
                        subTitle = "Vie quotidienne - Autres",
                        amount = "-8.00 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_shower_24dp,
                        iconInfoColor = semantic.neutral,
                        title = "Retrait distributeur caisse federale de C du 11/01",
                        subTitle = "Retrait d'espéces",
                        amount = "-800.00 €",
                    ),
                    SimpleValueRow.Data(
                        iconInfoId = R.drawable.cat_bar_24dp,
                        iconInfoColor = semantic.info,
                        title = "Paiement cb airbnb (Quartier Rouge Pays-Bas)",
                        subTitle = "Voyages, vacances",
                        amount = "-478.49 €",
                    ),
                )
            ),
        )
    }

}