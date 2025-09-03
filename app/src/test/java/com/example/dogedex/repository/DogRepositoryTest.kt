package com.example.dogedex.repository

import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.api.ApiService
import com.example.dogedex.api.dto.AddDogToUserDTO
import com.example.dogedex.core.api.dto.DogDTO
import com.example.dogedex.api.dto.LoginDTO
import com.example.dogedex.api.dto.SignUpDTO
import com.example.dogedex.api.responses.AuthApiResponse
import com.example.dogedex.api.responses.DefaultResponse
import com.example.dogedex.api.responses.DogApiResponse
import com.example.dogedex.api.responses.DogListApiResponse
import com.example.dogedex.api.responses.DogListResponse
import com.example.dogedex.api.responses.DogResponse
import com.example.dogedex.core.doglist.DogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import java.net.UnknownHostException


class DogRepositoryTest {

    private lateinit var dogRepository: DogRepository

    @Test
    fun `when calls getDogCollection returns dogList success`() = runTest {

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        listOf(
                            com.example.dogedex.core.api.dto.DogDTO(
                                1, 1, "", "", "", "",
                                "", "", "", "", ""
                            ),
                            com.example.dogedex.core.api.dto.DogDTO(
                                2, 2, "", "", "", "",
                                "", "", "", "", ""
                            )
                        )
                    )
                )
            }

            override suspend fun signUpUser(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        listOf(
                            com.example.dogedex.core.api.dto.DogDTO(
                                2, 2, "", "", "", "",
                                "", "", "", "", ""
                            )
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        dogRepository =
            DogRepository(apiService = FakeApiService(), dispatcher = Dispatchers.Unconfined)

        val apiResponse = dogRepository.getDogCollection()
        assert(apiResponse is ApiResponseStatus.Success)
        assertEquals(2, (apiResponse as ApiResponseStatus.Success).data.size)
    }

    @Test
    fun `when calls getDogCollection returns UnknownHostException and ApiResponseStatus is Error`() =
        runTest {

            class FakeApiService : ApiService {
                override suspend fun getAllDogs(): DogListApiResponse {
                    throw UnknownHostException()
                }

                override suspend fun signUpUser(signUpDTO: SignUpDTO): AuthApiResponse {
                    TODO("Not yet implemented")
                }

                override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                    TODO("Not yet implemented")
                }

                override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                    TODO("Not yet implemented")
                }

                override suspend fun getUserDogs(): DogListApiResponse {
                    return DogListApiResponse(
                        message = "",
                        isSuccess = true,
                        data = DogListResponse(
                            listOf(
                                com.example.dogedex.core.api.dto.DogDTO(
                                    2, 2, "", "", "", "",
                                    "", "", "", "", ""
                                )
                            )
                        )
                    )
                }

                override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                    TODO("Not yet implemented")
                }

            }

            dogRepository =
                DogRepository(apiService = FakeApiService(), dispatcher = Dispatchers.Unconfined)

            val apiResponse = dogRepository.getDogCollection()
            assert(apiResponse is ApiResponseStatus.Error)
            assertEquals(
                R.string.unknown_host_exception,
                (apiResponse as ApiResponseStatus.Error).messageId
            )
        }

    @Test
    fun `when calls getDogByMlId return DogRecognition and ApiResponse Success`() = runTest {

        val fakeIdDog = 2L

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUpUser(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
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
                return DogApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogResponse(
                        com.example.dogedex.core.api.dto.DogDTO(
                            2, 2, "", "", "", "",
                            "", "", "", "", ""
                        )
                    )
                )
            }

        }

        dogRepository = DogRepository(apiService = FakeApiService(), dispatcher = Dispatchers.Unconfined)

        val apiResponse = dogRepository.getDogByMlId("gfdsagdf")
        assert(apiResponse is ApiResponseStatus.Success)
        assertEquals(fakeIdDog, (apiResponse as ApiResponseStatus.Success).data.id)
    }


    @Test
    fun `when calls getDogByMlId return UnknownError and ApiResponse Error`() = runTest {

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUpUser(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
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
                return DogApiResponse(
                    message = "error_getting_dog_by_ml_id",
                    isSuccess = false,
                    data = DogResponse(
                        com.example.dogedex.core.api.dto.DogDTO(
                            2, 2, "", "", "", "",
                            "", "", "", "", ""
                        )
                    )
                )
            }

        }

        dogRepository = DogRepository(apiService = FakeApiService(), dispatcher = Dispatchers.Unconfined)

        val apiResponse = dogRepository.getDogByMlId("gfdsagdf")
        assert(apiResponse is ApiResponseStatus.Error)
        assertEquals(R.string.unknown_error, (apiResponse as ApiResponseStatus.Error).messageId)
    }
}