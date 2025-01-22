package com.example.dogedex.dogdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.doglist.DogRepository
import kotlinx.coroutines.launch

class DogDetailViewModel:ViewModel() {

    private val _uiState = MutableLiveData<ApiResponseStatus<Any>>()
    val uiState : LiveData<ApiResponseStatus<Any>>
        get() = _uiState

    private val dogRepository = DogRepository()

    fun addDogToUser(dogId : Long){
        viewModelScope.launch {
            _uiState.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        _uiState.value = apiResponseStatus
    }
}