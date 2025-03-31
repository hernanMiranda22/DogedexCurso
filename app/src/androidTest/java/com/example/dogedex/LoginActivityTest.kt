package com.example.dogedex

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.auth.AuthTasks
import com.example.dogedex.auth.LoginActivity
import com.example.dogedex.di.AuthTaskModule
import com.example.dogedex.model.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(AuthTaskModule::class)
@HiltAndroidTest
class LoginActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<LoginActivity>()


    class FakeAuthRepository @Inject constructor() : AuthTasks{
        override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
            return ApiResponseStatus.Success(
                User(1L, "prueba2025@gmail.com", "gfdgdfgbdfhjgd")
            )
        }

        override suspend fun signUp(
            email: String,
            password: String,
            passwordConfirm: String
        ): ApiResponseStatus<User> {
            TODO("Not yet implemented")
        }
    }


    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class AuthTaskTestModule {

        @Binds
        abstract fun bindAuthTask(
            fakeAuthRepository: FakeAuthRepository
        ) : AuthTasks
    }
    @Test
    fun whenPressedLoginButtonOpenMainActivity() = runTest {

        val context = composeRule.activity

        composeRule.onNodeWithText(context.getString(R.string.login)).assertIsDisplayed()
        composeRule.onNodeWithTag(useUnmergedTree = true ,testTag = "email-field").performTextInput("prueba2025@gmail.com")
        composeRule.onNodeWithTag(useUnmergedTree = true, testTag = "password-field").performTextInput("12345")
        composeRule.onNodeWithText(context.getString(R.string.login)).performClick()

        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
    }
}