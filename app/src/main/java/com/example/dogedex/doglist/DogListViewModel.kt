package com.example.dogedex.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.model.Dog
import com.example.dogedex.api.ApiResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(
    private val dogRepository : DogTask
):ViewModel() {

    var dogList = mutableStateOf<List<Dog>>(listOf())
        private set


    var uiState = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set


    init {
        getDogCollection()
    }

    private fun getDogCollection(){
        viewModelScope.launch {
            uiState.value = ApiResponseStatus.Loading()
           handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success){
            dogList.value = apiResponseStatus.data
        }
        uiState.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    fun resetApiResponse() {
        uiState.value = null
    }

}