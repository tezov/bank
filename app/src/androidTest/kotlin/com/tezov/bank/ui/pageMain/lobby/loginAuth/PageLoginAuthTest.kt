

package com.tezov.bank.ui.pageMain.lobby.loginAuth

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.tezov.bank.R
import com.tezov.bank.dev_utils.UtilsKeyboard
import com.tezov.bank.dev_utils.UtilsKeyboard.Status
import com.tezov.bank.ui.activity.MainActivity
import com.tezov.bank.ui.activity.MainActivityState
import com.tezov.bank.ui.pageMain.help.LoginScreen
import com.tezov.bank.ui.theme.ThemeApplication
import com.tezov.test_common.rule.LazyActivityScenarioRule.Companion.lazyActivityScenarioRule
import com.tezov.test_common.rule.RetryRule
import com.tezov.test_common.rule.RetryTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.Delegates

class PageLoginAuthTest {

    companion object {
        const val RETRY_IF_FAILED = 3
    }

    @get:Rule(order = 1)
    val retryRule = RetryRule()

    @get:Rule(order = 0)
    val scenarioRule = lazyActivityScenarioRule<ComponentActivity>()

    @get:Rule(order = 0)
    val composeRule = createEmptyComposeRule()

    var PAUSE_GLOBAL_ENABLED = false
    var PAUSE_ENABLED by Delegates.notNull<Boolean>()
    var PAUSE_DELAY_ms = 2000L

    var context by Delegates.notNull<Context>()
    var activityState by Delegates.notNull<MainActivityState>()
    var action by Delegates.notNull<LoginScreenAction>()
    var state by Delegates.notNull<LoginScreenState>()
    var isKeyboardOpen by Delegates.notNull<State<Status>>()

    @Before
    fun setup() {
        val intent =
            Intent(ApplicationProvider.getApplicationContext(), ComponentActivity::class.java)
        scenarioRule.launch(intent)
        scenarioRule.scenario.onActivity { activity ->
            activity.setContent {
                context = LocalContext.current
                isKeyboardOpen = UtilsKeyboard.AsState()
                activityState = MainActivityState.remember()
                action = activityState.provider.loginScreenAction()
                state = activityState.provider.loginScreenState(action)
                ThemeApplication.BankDefault {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        MainActivity.withBottomNavigationBar(
                            activityState,
                        ) {
                            LoginScreen(state, action, innerPadding = it)
                        }
                    }
                }
            }
        }
    }

    @After
    fun teardown() {
        scenarioRule.close()
    }

    private fun pause() {
        if (PAUSE_ENABLED || PAUSE_GLOBAL_ENABLED) {
            composeRule.waitForIdle()
            runTest { delay(PAUSE_DELAY_ms) }
        }
    }

    private class Nodes(context: Context, rule: ComposeTestRule) {
        val fld_login: SemanticsNodeInteraction
        val fld_password: SemanticsNodeInteraction
        val btn_connect: SemanticsNodeInteraction

        init {
            val labelLogin = context.resources.getString(R.string.screen_login_field_login)
            fld_login = rule.onNode(hasText(labelLogin))
            val labelPassword = context.resources.getString(R.string.screen_login_field_password)
            fld_password = rule.onNode(hasText(labelPassword))
            val labelConnect = context.resources.getString(R.string.screen_login_btn_connect)
            btn_connect = rule.onNode(hasText(labelConnect))
        }
    }

    @RetryTest(RETRY_IF_FAILED)
    @Test
    fun login_components_are_displayed() {
        PAUSE_ENABLED = false
        Nodes(context, composeRule).run {
            fld_login.assertIsDisplayed()
            fld_password.assertIsDisplayed()
            btn_connect.assertIsDisplayed()
        }
    }

    @RetryTest(RETRY_IF_FAILED)
    @Test
    fun button_connect_with_login_correct_activation() {
        PAUSE_ENABLED = false
        Nodes(context, composeRule).run {
            // [null, null]
            btn_connect.assertIsNotEnabled()
            fld_login.performClick()
            pause()
            btn_connect.assertIsNotEnabled()
            // [null, password]
            fld_password.performTextReplacement("password")
            fld_login.performClick()
            pause()
            btn_connect.assertIsNotEnabled()
            // [login, password]
            fld_login.performTextReplacement("login")
            pause()
            btn_connect.assertIsEnabled()
            // [login, null]
            fld_password.performTextReplacement("")
            fld_login.performClick()
            pause()
            btn_connect.assertIsNotEnabled()
            // [null, null]
            fld_login.performTextReplacement("")
            pause()
            btn_connect.assertIsNotEnabled()
        }
    }

    @RetryTest(RETRY_IF_FAILED)
    @Test
    fun button_connect_with_password_correct_activation() {
        PAUSE_ENABLED = false
        Nodes(context, composeRule).run {
            // [null, null]
            btn_connect.assertIsNotEnabled()
            fld_password.performClick()
            pause()
            btn_connect.assertIsNotEnabled()
            // [login, null]
            fld_login.performTextReplacement("login")
            fld_password.performClick()
            pause()
            btn_connect.assertIsNotEnabled()
            // [login, password]
            fld_password.performTextReplacement("password")
            pause()
            btn_connect.assertIsEnabled()
            // [login, null]
            fld_login.performTextReplacement("")
            fld_password.performClick()
            pause()
            btn_connect.assertIsNotEnabled()
            // [null, null]
            fld_password.performTextReplacement("")
            pause()
            btn_connect.assertIsNotEnabled()
        }
    }

    @RetryTest(RETRY_IF_FAILED)
    @Test
    fun keyboard_action_with_login_correct_focus() {
        PAUSE_ENABLED = false
        Nodes(context, composeRule).run {
            fld_login.assert(isNotFocused())
            Truth.assertThat(isKeyboardOpen.value).isEqualTo(Status.Closed)
            fld_login.performClick()
            pause()
            fld_login.assert(isFocused())
            Truth.assertThat(isKeyboardOpen.value).isEqualTo(Status.Opened)
            //todo check keyboardType
            fld_login.performTextReplacement("login")
            fld_login.performImeAction()
            pause()
            fld_login.assert(isNotFocused())
            fld_password.assert(isFocused())
            //Truth.assertThat(isKeyboardOpen.value).isEqualTo(Keyboard.Opened) //todo don't know why, in test environnement, the keyboard disappear
            pause()
        }
    }

    @RetryTest(RETRY_IF_FAILED)
    @Test
    fun keyboard_action_with_password_correct_focus() {
        PAUSE_ENABLED = false
        Nodes(context, composeRule).run {
            fld_password.assert(isNotFocused())
            Truth.assertThat(isKeyboardOpen.value).isEqualTo(Status.Closed)
            fld_password.performClick()
            pause()
            fld_password.assert(isFocused())
            Truth.assertThat(isKeyboardOpen.value).isEqualTo(Status.Opened)
            //todo check keyboardType
            fld_password.performTextReplacement("password")
            fld_password.performImeAction()
            pause()
            fld_password.assert(isNotFocused())
            fld_login.assert(isFocused())
            //Truth.assertThat(isKeyboardOpen.value).isEqualTo(Keyboard.Opened) //todo don't know why, in test environnement, the keyboard disappear
            pause()
        }
    }

    @RetryTest(RETRY_IF_FAILED)
    @Test
    fun keyboard_action_login_correct_ime() {
        PAUSE_ENABLED = false
        Nodes(context, composeRule).run {
            // [null, null]
            fld_login.performClick()
            pause()
            fld_login.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Next))
            // [null, password]
            fld_password.performTextReplacement("password")
            fld_login.performClick()
            pause()
            fld_login.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Done))
            fld_login.performTextReplacement("login")
            pause()
            fld_login.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Done))
            // [login, null]
            fld_password.performTextReplacement("")
            fld_login.performClick()
            pause()
            fld_login.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Next))
            // [null, null]
            fld_login.performTextReplacement("")
            pause()
            fld_login.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Next))
        }

    }

    @RetryTest(RETRY_IF_FAILED)
    @Test
    fun keyboard_action_password_correct_ime() {
        PAUSE_ENABLED = false
        Nodes(context, composeRule).run {
            // [null, null]
            fld_password.performClick()
            pause()
            fld_password.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Next))
            // [null, password]
            fld_login.performTextReplacement("login")
            fld_password.performClick()
            pause()
            fld_password.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Done))
            fld_password.performTextReplacement("password")
            pause()
            fld_password.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Done))
            // [login, null]
            fld_login.performTextReplacement("")
            fld_password.performClick()
            pause()
            fld_password.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Next))
            // [null, null]
            fld_password.performTextReplacement("")
            pause()
            fld_password.assert(hasImeAction(androidx.compose.ui.text.input.ImeAction.Next))
        }

    }


}