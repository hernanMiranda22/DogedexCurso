package com.example.dogedex.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.doglist.DogRepository
import com.example.dogedex.doglist.DogTask
import com.example.dogedex.machinelearning.Classifier
import com.example.dogedex.machinelearning.ClassifierRepository
import com.example.dogedex.machinelearning.ClassifierTask
import com.example.dogedex.machinelearning.DogRecognition
import com.example.dogedex.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dogRepository : DogTask,
    private val classifierRepository : ClassifierTask
) :ViewModel() {

    private val _dog = MutableLiveData<Dog>()
    val dog :LiveData<Dog>
        get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status : LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _dogRecognized = MutableLiveData<DogRecognition>()
    val dogRecognized : LiveData<DogRecognition>
        get() = _dogRecognized


    fun recognizedImage(imageProxy: ImageProxy){
        viewModelScope.launch {
            _dogRecognized.value = classifierRepository.recognizedImage(imageProxy)

            imageProxy.close()
        }
    }

    fun getDogByMlId(mlDogId : String){
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handlerResponseStatus(dogRepository.getDogByMlId(mlDogId))
        }
    }

    private fun handlerResponseStatus(apiResponseStatus: ApiResponseStatus<Dog>){
        if (apiResponseStatus is ApiResponseStatus.Success){
            _dog.value = apiResponseStatus.data
        }
        _status.value = apiResponseStatus
    }
}