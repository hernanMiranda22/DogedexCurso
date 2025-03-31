package com.example.dogedex

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.auth.AuthScreen
import com.example.dogedex.auth.AuthTasks
import com.example.dogedex.auth.AuthViewModel
import com.example.dogedex.model.User
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class AuthScreenTest {
    private lateinit var viewModel: AuthViewModel

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun whenTapRegisterButtonOpenSignUpScreen() = runTest {

        class FakeAuthRepository : AuthTasks{
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirm: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }
        }

        viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        composeRule.setContent {
            AuthScreen(
                onUserLoggedIn = {},
                authViewModel = viewModel
            )
        }

        composeRule.onNodeWithTag(testTag = "login-button").isDisplayed()
        composeRule.onNodeWithTag(testTag = "login-screen-register-button").performClick()
        composeRule.onNodeWithTag(testTag = "signUp-button").isDisplayed()
    }

    @Test
    fun whenTapLoginButtonAndEmailFieldIsEmptyShowErrorMessage() = runTest {

        class FakeAuthRepository : AuthTasks{
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirm: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }
        }

        viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        composeRule.setContent {
            AuthScreen(
                onUserLoggedIn = {},
                authViewModel = viewModel
            )
        }

        composeRule.onNodeWithTag(testTag = "login-button").performClick()
        composeRule.onNodeWithTag(useUnmergedTree = true,testTag = "email-field-error").isDisplayed()
        composeRule.onNodeWithTag(useUnmergedTree = true ,testTag = "email-field").performTextInput("prueba2025@gmail.com")
        composeRule.onNodeWithTag(useUnmergedTree = true ,testTag = "login-button").performClick()
        composeRule.onNodeWithTag(useUnmergedTree = true ,testTag = "password-field-error").isDisplayed()
    }
}