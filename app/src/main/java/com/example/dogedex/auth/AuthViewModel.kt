package com.example.dogedex.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository : AuthTasks
) :ViewModel() {

    //private val authRepository = AuthRepository()

    var user = mutableStateOf<User?>(null)
        private set

    var uiState = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.login(email, password))
        }
    }

    fun signUp(email : String, password: String, passwordConfirm : String){
        viewModelScope.launch {
           uiState.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.signUp(email, password, passwordConfirm))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>){
        if (apiResponseStatus is ApiResponseStatus.Success){
            user.value = apiResponseStatus.data
        }
        uiState.value = apiResponseStatus
    }

    fun resetApiResponse() {
        uiState.value = null
    }


}