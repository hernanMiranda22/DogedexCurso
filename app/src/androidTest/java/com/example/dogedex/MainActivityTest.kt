package com.example.dogedex

import android.Manifest
import androidx.camera.core.ImageProxy
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.camera.di.ClassifierModule
import com.example.dogedex.core.di.DogTaskModule
import com.example.dogedex.core.doglist.DogTask
import com.example.dogedex.camera.machinelearning.ClassifierTask
import com.example.dogedex.camera.machinelearning.DogRecognition
import com.example.dogedex.camera.main.MainActivity
import com.example.dogedex.core.testutils.EspressoIdlingResource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(DogTaskModule::class, com.example.dogedex.camera.di.ClassifierModule::class)
@HiltAndroidTest
class MainActivityTest {


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val runtimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @get:Rule(order = 2)
    val composeRule = createComposeRule()

    @get:Rule(order = 3)
    val activityScenarioRule = ActivityScenarioRule(com.example.dogedex.camera.main.MainActivity::class.java)


    @Before
    fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)

    }

    class FakeDogRepository @Inject constructor() : DogTask {
        override suspend fun getDogCollection(): ApiResponseStatus<List<com.example.dogedex.core.model.Dog>> {
            return ApiResponseStatus.Success(
                listOf(
                    com.example.dogedex.core.model.Dog(
                        1, 1, "", "", "", "",
                        "", "", "", "", "",
                        inCollection = true
                    ),
                    com.example.dogedex.core.model.Dog(
                        2, 2, "", "", "", "",
                        "", "", "", "", "",
                        inCollection = true
                    )
                )
            )
        }

        override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
            TODO("Not yet implemented")
        }

        override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<com.example.dogedex.core.model.Dog> {
            return ApiResponseStatus.Success(
                com.example.dogedex.core.model.Dog(
                    89, 20, "Chihuahua", "", "", "",
                    "", "", "", "", "",
                    inCollection = true
                )
            )
        }
    }

    class FakeClassifierRepository @Inject constructor() :
        com.example.dogedex.camera.machinelearning.ClassifierTask {
        override suspend fun recognizedImage(imageProxy: ImageProxy): com.example.dogedex.camera.machinelearning.DogRecognition {
            return com.example.dogedex.camera.machinelearning.DogRecognition("gfdgfdgdf", 100.0f)
        }
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class DogTaskTestModule {

        @Binds
        abstract fun bindAnalyticsService(
            fakeDogRepository: FakeDogRepository
        ): DogTask
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class ClassifierTestModule{

        @Binds
        abstract fun bindClassifierTask(
            fakeClassifierRepository: FakeClassifierRepository
        ) : com.example.dogedex.camera.machinelearning.ClassifierTask
    }



    @Test
    fun showAllFab() = runTest {
        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.dog_list_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.setting_fab)).check(matches(isDisplayed()))
    }

    @Test
    fun whenClickDogListFabOpenDogListScreen() = runTest {
        onView(withId(R.id.dog_list_fab)).perform(click())

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val string = context.getString(R.string.my_dog_collection)

        composeRule.onNodeWithText(string).assertIsDisplayed()
    }

    @Test
    fun whenRecognizingDogDetailsScreenOpens() = runTest {
        onView(withId(R.id.take_photo_fab)).perform(click())

        composeRule.onNodeWithTag(testTag = "close-details-screen-fab").assertIsDisplayed()
    }
}