package com.example.dogedex.repository

import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.api.ApiService
import com.example.dogedex.api.dto.AddDogToUserDTO
import com.example.dogedex.api.dto.LoginDTO
import com.example.dogedex.api.dto.SignUpDTO
import com.example.dogedex.api.dto.UserDTO
import com.example.dogedex.api.responses.AuthApiResponse
import com.example.dogedex.api.responses.DefaultResponse
import com.example.dogedex.api.responses.DogApiResponse
import com.example.dogedex.api.responses.DogListApiResponse
import com.example.dogedex.api.responses.UserResponse
import com.example.dogedex.core.auth.AuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import java.net.UnknownHostException


class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository

    @Test
    fun `when calls login returns ApiResponseStatus success`() = runTest {

        val emailFake = "gfdgdfg@gmail.com"
        val passwordFake = "123456"

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUpUser(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                return AuthApiResponse(
                    message = "",
                    isSuccess = true,
                    data = UserResponse(
                        UserDTO(
                            id = 1L,
                            email = "gfdgdfg@gmail.com",
                            authenticationToken = "gdfgdfgdfgdf"
                        )
                    )
                )
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        authRepository = AuthRepository(apiService = FakeApiService())

        val authResponse = authRepository.login(email = emailFake, password =  passwordFake)

        assert(authResponse is ApiResponseStatus.Success)
        assertEquals(emailFake, (authResponse as ApiResponseStatus.Success).data.email)
    }

    @Test
    fun `when calls login returns UnknownHostException and ApiResponseStatus is error`() = runTest {

        val emailFake = "gfdgdfg@gmail.com"
        val passwordFake = "123456"

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUpUser(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                throw UnknownHostException()
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        authRepository = AuthRepository(apiService = FakeApiService())

        val authResponse = authRepository.login(email = emailFake, password =  passwordFake)

        assert(authResponse is ApiResponseStatus.Error)
        assertEquals(
            R.string.unknown_host_exception,
            (authResponse as ApiResponseStatus.Error).messageId)
    }

    @Test
    fun `when calls signUp returns ApiResponseStatus success`() = runTest {

        val emailFake = "gfdgdfg@gmail.com"
        val passwordFake = "123456"
        val passwordConfirmFake = "123456"

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUpUser(signUpDTO: SignUpDTO): AuthApiResponse {
                return AuthApiResponse(
                    message = "",
                    isSuccess = true,
                    data = UserResponse(
                        UserDTO(
                            id = 1L,
                            email = "gfdgdfg@gmail.com",
                            authenticationToken = "gdfgdfgdfgdf"
                        )
                    )
                )
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        authRepository = AuthRepository(apiService = FakeApiService())

        val authResponse = authRepository.signUp(emailFake, passwordFake, passwordConfirmFake)

        assert(authResponse is ApiResponseStatus.Success)
        assertEquals(
            emailFake,
            (authResponse as ApiResponseStatus.Success).data.email)
    }

    @Test
    fun `when calls signUp returns UnknownError and ApiResponseStatus is error`() = runTest {

        val emailFake = "gfdgdfg@gmail.com"
        val passwordFake = "123456"

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUpUser(signUpDTO: SignUpDTO): AuthApiResponse {
                return AuthApiResponse(
                    message = "error_sign_up_user_with_email",
                    isSuccess = false,
                    data = UserResponse(
                        UserDTO(
                            id = 1L,
                            email = "gfdgdfg@gmail.com",
                            authenticationToken = "gdfgdfgdfgdf"
                        )
                    )
                )
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        authRepository = AuthRepository(apiService = FakeApiService())

        val authResponse = authRepository.signUp(email = emailFake, password =  passwordFake, passwordFake)

        assert(authResponse is ApiResponseStatus.Error)
        assertEquals(
            R.string.unknown_error,
            (authResponse as ApiResponseStatus.Error).messageId)
    }
}