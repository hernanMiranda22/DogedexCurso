package com.example.dogedex.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.model.Dog
import com.example.dogedex.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel:ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList : LiveData<List<Dog>>
        get() = _dogList

    private val _uiState = MutableLiveData<ApiResponseStatus<Any>>()
    val uiState : LiveData<ApiResponseStatus<Any>>
        get() = _uiState

    private val dogRepository = DogRepository()

    init {
        getDogCollection()
    }

    fun addDogToUser(dogId : Long){
        viewModelScope.launch {
            _uiState.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun getDogCollection(){
        viewModelScope.launch {
            _uiState.value = ApiResponseStatus.Loading()
           handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success){
            _dogList.value = apiResponseStatus.data
        }
        _uiState.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        if (apiResponseStatus is ApiResponseStatus.Success){
            getDogCollection()
        }
        _uiState.value = apiResponseStatus
    }
}