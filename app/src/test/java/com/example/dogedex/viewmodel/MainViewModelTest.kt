package com.example.dogedex.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.camera.core.ImageProxy
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.core.doglist.DogRepository
import com.example.dogedex.camera.machinelearning.ClassifierRepository
import com.example.dogedex.camera.machinelearning.DogRecognition
import com.example.dogedex.camera.main.MainViewModel
import com.example.dogedex.core.model.Dog
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule


@ExperimentalCoroutinesApi
class MainViewModelTest {

    @RelaxedMockK
    private lateinit var dogRepository: DogRepository
    @RelaxedMockK
    private lateinit var classifierRepository : com.example.dogedex.camera.machinelearning.ClassifierRepository
    @RelaxedMockK
    private lateinit var imageProxy: ImageProxy


    private lateinit var viewModel: com.example.dogedex.camera.main.MainViewModel



    @get:Rule
    val rule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val idMlDog = "djshfjsahfjs"
    private val idDog = 1L
    private val index = 1
    private val confidence = 0.5f


    @Before
    fun setup(){
        MockKAnnotations.init(this)
        viewModel =
            com.example.dogedex.camera.main.MainViewModel(dogRepository, classifierRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }


    @Test
    fun `when the user take a photo send machine learning id and api status is success`() = runTest {

        val fakeDog = com.example.dogedex.core.model.Dog(
            idDog, index, "", "", "", "",
            "", "", "", "", "",
            inCollection = false
        )

        coEvery { dogRepository.getDogByMlId(idMlDog) } returns ApiResponseStatus.Success(fakeDog)

        viewModel.getDogByMlId(idMlDog)

        assert(viewModel.status.value is ApiResponseStatus.Success)

    }

    @Test
    fun `when the user is using the camera and recognize the dog repository return dogRecognition`() = runTest {

        val fakeDogRecognition =
            com.example.dogedex.camera.machinelearning.DogRecognition(idMlDog, confidence)

        coEvery { classifierRepository.recognizedImage(imageProxy) } returns fakeDogRecognition

        viewModel.recognizedImage(imageProxy)

        assertEquals(fakeDogRecognition.id, viewModel.dogRecognized.value?.id)

    }
}