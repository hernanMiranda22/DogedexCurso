package com.example.dogedex.dogdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.dogdetail.DogDetailComposeActivity.Companion.MOST_PROBABLE_DOGS_IDS
import com.example.dogedex.doglist.DogTask
import com.example.dogedex.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository : DogTask,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    var dog : MutableState<Dog> = mutableStateOf(
        savedStateHandle[DogDetailComposeActivity.DOG_DETAIL]!!
    )
        private set

    private var probableDogsIds = mutableStateOf(
        savedStateHandle.get<ArrayList<String>>(MOST_PROBABLE_DOGS_IDS) ?: arrayListOf()
    )

    var isRecognition : MutableState<Boolean> = mutableStateOf(
        savedStateHandle[DogDetailComposeActivity.IS_RECOGNIZED_KEY] ?: false
    )
        private set

    var uiState = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    private var _probableDogList = MutableStateFlow<MutableList<Dog>>(mutableListOf())
    val probableDogList : StateFlow<MutableList<Dog>>
        get() = _probableDogList

    fun updateDog(newDog : Dog){
        dog.value = newDog
    }

    fun getProbableDogs(){
        viewModelScope.launch {
            dogRepository.getProbableDogs(probableDogsIds.value)
                .collect{ apiResponseStatus ->
                    if (apiResponseStatus is ApiResponseStatus.Success){
                        val probableDogMutableList = _probableDogList.value.toMutableList()
                        probableDogMutableList.add(apiResponseStatus.data)
                        _probableDogList.value = probableDogMutableList
                    }
            }
        }
    }

    fun addDogToUser(){
        viewModelScope.launch {
            uiState.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dog.value.id))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        uiState.value = apiResponseStatus
    }

    fun resetApiResponse() {
        uiState.value = null
    }
}