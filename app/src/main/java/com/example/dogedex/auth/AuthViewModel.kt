package com.example.dogedex.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.model.User
import kotlinx.coroutines.launch

class AuthViewModel:ViewModel() {

    private val authRepository = AuthRepository()

    private val _userLiveData = MutableLiveData<User>()
    val userLivedata : LiveData<User>
        get() = _userLiveData

    private val _uiState = MutableLiveData<ApiResponseStatus<User>>()
    val uiState : LiveData<ApiResponseStatus<User>>
        get() = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.login(email, password))
        }
    }

    fun signUp(email : String, password: String, passwordConfirm : String){
        viewModelScope.launch {
           _uiState.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.signUp(email, password, passwordConfirm))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>){
        if (apiResponseStatus is ApiResponseStatus.Success){
            _userLiveData.value = apiResponseStatus.data
        }
        _uiState.value = apiResponseStatus
    }


}