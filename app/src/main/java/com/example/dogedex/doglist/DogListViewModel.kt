package com.example.dogedex.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.Dog
import com.example.dogedex.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel:ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList : LiveData<List<Dog>>
        get() = _dogList

    private val _uiState = MutableLiveData<ApiResponseStatus<List<Dog>>>()
    val uiState : LiveData<ApiResponseStatus<List<Dog>>>
        get() = _uiState

    private val dogRepository = DogRepository()

    init {
        downLoadDogs()
    }

    private fun downLoadDogs() {
        viewModelScope.launch {
            _uiState.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.downloadDogs())
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success){
            _dogList.value = apiResponseStatus.data
        }
        _uiState.value = apiResponseStatus
    }
}