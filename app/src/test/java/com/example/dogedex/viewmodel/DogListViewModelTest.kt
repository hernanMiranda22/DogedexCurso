package com.example.dogedex.viewmodel

import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.core.doglist.DogListViewModel
import com.example.dogedex.core.doglist.DogTask
import com.example.dogedex.core.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DogListViewModelTest {

    private lateinit var viewModel : DogListViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when repository return dogList apiResponse is success`() = runTest {

        class FakeRepository : DogTask {
            override suspend fun getDogCollection(): ApiResponseStatus<List<com.example.dogedex.core.model.Dog>> {

                return ApiResponseStatus.Success(
                    listOf(
                        com.example.dogedex.core.model.Dog(
                            1, 1, "", "", "", "",
                            "", "", "", "", "",
                            inCollection = false
                        ),
                        com.example.dogedex.core.model.Dog(
                            2, 2, "", "", "", "",
                            "", "", "", "", "",
                            inCollection = false
                        )
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<com.example.dogedex.core.model.Dog> {
                return ApiResponseStatus.Success(
                    com.example.dogedex.core.model.Dog(
                        1, 1, "", "", "", "",
                        "", "", "", "", "",
                        inCollection = false
                    )
                )
            }

        }

        viewModel = DogListViewModel(FakeRepository())

        assertEquals(2, viewModel.dogList.value.size)
        assert(viewModel.uiState.value is ApiResponseStatus.Success)
    }

    @Test
    fun `when repository return error apiResponse is error`() = runTest {

        class FakeRepository : DogTask {
            override suspend fun getDogCollection(): ApiResponseStatus<List<com.example.dogedex.core.model.Dog>> {

                return ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<com.example.dogedex.core.model.Dog> {
                return ApiResponseStatus.Error(messageId = 12)
            }

        }

        viewModel = DogListViewModel(FakeRepository())

        assertEquals(0, viewModel.dogList.value.size)
        assert(viewModel.uiState.value is ApiResponseStatus.Error)
    }

    @Test
    fun `when repository return state apiResponse is reset`() = runTest {

        class FakeRepository : DogTask {
            override suspend fun getDogCollection(): ApiResponseStatus<List<com.example.dogedex.core.model.Dog>> {

                return ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<com.example.dogedex.core.model.Dog> {
                return ApiResponseStatus.Error(messageId = 12)
            }

        }

        viewModel = DogListViewModel(FakeRepository())

        assert(viewModel.uiState.value is ApiResponseStatus.Error)

        viewModel.resetApiResponse()

        assert(viewModel.uiState.value == null)

    }
}
