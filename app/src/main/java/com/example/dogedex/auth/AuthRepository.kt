package com.example.dogedex.auth

import com.example.dogedex.model.User
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.api.DogsApi.retrofitService
import com.example.dogedex.api.dto.LoginDTO
import com.example.dogedex.api.dto.SignUpDTO
import com.example.dogedex.api.dto.UserDTOMapper
import com.example.dogedex.api.makeNetworkCall

class AuthRepository {

    suspend fun login(
        email : String,
        password: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val loginDTO = LoginDTO(email,password)
        val loginResponse  = retrofitService.login(loginDTO)

        if (!loginResponse.isSuccess){
            throw Exception(loginResponse.message)
        }

        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDtoToUserDomain(userDTO)

    }

    suspend fun signUp(
        email : String,
        password: String,
        passwordConfirm : String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email,password, passwordConfirm)
        val signUpResponse  = retrofitService.signUpUser(signUpDTO)

        if (!signUpResponse.isSuccess){
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDtoToUserDomain(userDTO)
        
    }
}