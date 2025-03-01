package com.example.dogedex.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.doglist.DogTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository : DogTask
):ViewModel() {

    var uiState = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    //private val dogRepository = DogRepository()

    fun addDogToUser(dogId : Long){
        viewModelScope.launch {
            uiState.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        uiState.value = apiResponseStatus
    }

    fun resetApiResponse() {
        uiState.value = null
    }
}