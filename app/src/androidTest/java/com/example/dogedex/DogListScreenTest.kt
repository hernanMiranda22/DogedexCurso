package com.example.dogedex

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.core.api.ApiResponseStatus
import com.example.dogedex.core.doglist.DogListScreen
import com.example.dogedex.core.doglist.DogListViewModel
import com.example.dogedex.core.doglist.DogTask
import com.example.dogedex.core.model.Dog
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DogListScreenTest {

    private lateinit var viewModel: DogListViewModel

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun whenRepositoryReturnApiStatusLoadingUiShowLoadingWheel() = runTest {

        class FakeDogRepository : DogTask {
            override suspend fun getDogCollection(): ApiResponseStatus<List<com.example.dogedex.core.model.Dog>> {
                return ApiResponseStatus.Loading()
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<com.example.dogedex.core.model.Dog> {
                TODO("Not yet implemented")
            }
        }

        viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeRule.setContent {
            DogListScreen(
                onNavigationIconClick = {},
                onDogClicked = {},
                viewModel = viewModel
            )
        }

        composeRule.onNodeWithTag(testTag = "loading-wheel").assertIsEnabled()
    }

    @Test
    fun whenRepositoryReturnApiStatusErrorShowAlertDialogMessage() = runTest {

        class FakeDogRepository : DogTask {
            override suspend fun getDogCollection(): ApiResponseStatus<List<com.example.dogedex.core.model.Dog>> {
                return ApiResponseStatus.Error(messageId = R.string.there_was_an_error)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<com.example.dogedex.core.model.Dog> {
                TODO("Not yet implemented")
            }
        }

        viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeRule.setContent {
            DogListScreen(
                onNavigationIconClick = {},
                onDogClicked = {},
                viewModel = viewModel
            )
        }

        //Compose rule tiene una desventaja que los test por text, por ejemplo, solamente funcionan si son textos en ingles
        //composeRule.onNodeWithText(text = "There was an error").assertIsEnabled()
        composeRule.onNodeWithTag(testTag = "error-dialog").assertIsEnabled()
    }


    @Test
    fun whenCallsGetDogCollectionAndReturnsApiResponseStatusSuccessShowDogList() = runTest {

        val dog1Name = "Chihuahua"
        val dog2Name = "Juan"


        class FakeDogRepository : DogTask {
            override suspend fun getDogCollection(): ApiResponseStatus<List<com.example.dogedex.core.model.Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        com.example.dogedex.core.model.Dog(
                            1, 1, dog1Name, "", "", "",
                            "", "", "", "", "",
                            inCollection = true
                        ),
                        com.example.dogedex.core.model.Dog(
                            2, 2, dog2Name, "", "", "",
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
                TODO("Not yet implemented")
            }
        }

        viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeRule.setContent {
            DogListScreen(
                onNavigationIconClick = {},
                onDogClicked = {},
                viewModel = viewModel
            )
        }

        //composeRule.onRoot(useUnmergedTree = true).printToLog("MANZANA")
        composeRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-${dog1Name}").assertIsEnabled()
        composeRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-${dog2Name}").assertIsEnabled()
    }
}