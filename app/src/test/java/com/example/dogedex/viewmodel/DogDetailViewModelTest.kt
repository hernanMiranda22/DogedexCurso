package com.example.dogedex.viewmodel

import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.core.dogdetail.DogDetailViewModel
import com.example.dogedex.core.doglist.DogTask
import com.example.dogedex.core.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DogDetailViewModelTest {

    private lateinit var viewModel: DogDetailViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when user add favorite dog api status is success`() = runTest {

        val dogFake = com.example.dogedex.core.model.Dog(
            1, 1, "", "", "", "",
            "", "", "", "", "",
            inCollection = false
        )

        class FakeDogRepository: DogTask {
            override suspend fun getDogCollection(): ApiResponseStatus<List<com.example.dogedex.core.model.Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        dogFake,
                        dogFake
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(dogFake.id)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<com.example.dogedex.core.model.Dog> {
                return ApiResponseStatus.Success(
                    dogFake
                )
            }
        }

        viewModel = DogDetailViewModel(FakeDogRepository())

        viewModel.addDogToUser(1L)

        assert(viewModel.uiState.value is ApiResponseStatus.Success)
    }

    @Test
    fun `when user add a dog to favorite and repository return an error api status is error`() = runTest {

        class FakeDogRepository: DogTask {
            override suspend fun getDogCollection(): ApiResponseStatus<List<com.example.dogedex.core.model.Dog>> {
                return ApiResponseStatus.Error(30)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Error(31)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<com.example.dogedex.core.model.Dog> {
                return ApiResponseStatus.Error(32)
            }
        }

        viewModel = DogDetailViewModel(FakeDogRepository())

        viewModel.addDogToUser(1L)

        assert(viewModel.uiState.value is ApiResponseStatus.Error)
    }
}