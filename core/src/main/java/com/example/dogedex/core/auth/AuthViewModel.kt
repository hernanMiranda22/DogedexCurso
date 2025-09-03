package com.example.dogedex.core.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.core.R
import com.example.dogedex.core.api.ApiResponseStatus
import com.example.dogedex.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository : AuthTasks
) :ViewModel() {

    var user = mutableStateOf<User?>(null)
        private set

    var emailError = mutableStateOf<Int?>(null)
        private set

    var passwordError = mutableStateOf<Int?>(null)
        private set

    var confirmPasswordError = mutableStateOf<Int?>(null)
        private set

    var uiState = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    fun login(email: String, password: String) {

        when{
            email.isEmpty() -> {
                emailError.value = R.string.email_is_no_valid
            }
            password.isEmpty() -> {
                passwordError.value = R.string.password_must_not_be_empty
            }
            else -> {
                viewModelScope.launch {
                    uiState.value = ApiResponseStatus.Loading()
                    handleResponseStatus(authRepository.login(email, password))
                }
            }
        }


    }

    fun signUp(email : String, password: String, passwordConfirm : String){

        when{
            email.isEmpty() -> {
                emailError.value = R.string.email_is_no_valid
            }
            password.isEmpty() -> {
                passwordError.value = R.string.password_must_not_be_empty
            }
            passwordConfirm.isEmpty() -> {
                confirmPasswordError.value = R.string.password_must_not_be_empty
            }
            passwordConfirm != password ->{
                passwordError.value = R.string.passwords_do_not_match
                confirmPasswordError.value = R.string.passwords_do_not_match
            }
            else -> {
                viewModelScope.launch {
                    uiState.value = ApiResponseStatus.Loading()
                    handleResponseStatus(authRepository.signUp(email, password, passwordConfirm))
                }
            }
        }

    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>){
        if (apiResponseStatus is ApiResponseStatus.Success){
            user.value = apiResponseStatus.data
        }
        uiState.value = apiResponseStatus
    }

    fun resetErrors(){
        emailError.value = null
        passwordError.value = null
        confirmPasswordError .value = null
    }

    fun resetApiResponse() {
        uiState.value = null
    }

}