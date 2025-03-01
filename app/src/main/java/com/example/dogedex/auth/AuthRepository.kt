package com.example.dogedex.auth

import com.example.dogedex.model.User
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.api.ApiService
import com.example.dogedex.api.dto.LoginDTO
import com.example.dogedex.api.dto.SignUpDTO
import com.example.dogedex.api.dto.UserDTOMapper
import com.example.dogedex.api.makeNetworkCall
import javax.inject.Inject

interface AuthTasks {

    suspend fun login(
        email : String,
        password: String
    ): ApiResponseStatus<User>

    suspend fun signUp(
        email : String,
        password: String,
        passwordConfirm : String
    ): ApiResponseStatus<User>

}


class AuthRepository @Inject constructor(
    private val apiService: ApiService
) : AuthTasks {

    override suspend fun login(
        email : String,
        password: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val loginDTO = LoginDTO(email,password)
        val loginResponse  = apiService.login(loginDTO)

        if (!loginResponse.isSuccess){
            throw Exception(loginResponse.message)
        }

        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDtoToUserDomain(userDTO)

    }

    override suspend fun signUp(
        email : String,
        password: String,
        passwordConfirm : String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email,password, passwordConfirm)
        val signUpResponse  = apiService.signUpUser(signUpDTO)

        if (!signUpResponse.isSuccess){
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDtoToUserDomain(userDTO)
        
    }
}