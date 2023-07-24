

package com.tezov.bank.ui.pageSecondary.auth.messageInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tezov.bank.ui.component.block.SectionMessageRow
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry
import com.tezov.lib_core_android_kotlin.ui.component.block.HorizontalPager
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_core_android_kotlin.ui.extension.ExtensionCompositionLocal
import com.tezov.lib_core_kotlin.type.collection.ListEntry

object PageMessageInfo : Page<PageMessageInfoState, PageMessageInfoAction> {

    @Composable
    override fun Page<PageMessageInfoState, PageMessageInfoAction>.content(graphEntry: GraphEntry, innerPadding: PaddingValues) {
        val accessor = DiAccessorAppUiPage().with(key = this).contextMessageInfo().apply {
            remember()
        }
        val state = accessor.state()
        val action = accessor.action()
        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PageMessageInfoTheme provides PageMessageInfoTheme.provideColors(),
            ),
            child = {
                arrayOf(
                    PageMessageInfoTheme provides PageMessageInfoTheme.provideStyles(),
                )
            }
        ) {
            val tabTitles = remember(state.header) {
                ListEntry<HorizontalPager.WithTabRow.Tab, @Composable () -> Unit>().apply {
                    state.header?.let { header ->
                        header.tabNotification?.let {
                            add(it) {
                                TabNotification(
                                    action = action,
                                    messages = state.messages
                                )
                            }
                        }
                        header.tabMessageBox?.let {
                            add(it) {
                                TabMessageBox()
                            }
                        }
                    }
                }
            }
            HorizontalPager.WithTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PageMessageInfoTheme.colors.background)
                    .padding(innerPadding),
                style = PageMessageInfoTheme.styles.pagerTabRow,
                items = tabTitles
            )
        }
    }

    @Composable
    private fun TabNotification(
        action: PageMessageInfoAction,
        messages: SectionMessageRow.Data?,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            messages?.let {
                SectionMessageRow( //TODO lazy column for this section and maybe the other too
                    modifier = Modifier
                        .fillMaxSize(),
                    data = messages,
                    style = PageMessageInfoTheme.styles.sectionRow,
                    onClick = action::onClickMessage
                )
            }
        }
    }

    @Composable
    private fun TabMessageBox() {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Not Implemented",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}