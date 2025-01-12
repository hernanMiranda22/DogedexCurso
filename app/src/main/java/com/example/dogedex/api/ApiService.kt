package com.example.dogedex.api

import com.example.dogedex.ADD_DOG_TO_USER_URL
import com.example.dogedex.BASE_URL
import com.example.dogedex.GET_ALL_DOGS
import com.example.dogedex.GET_USER_DOGS_URL
import com.example.dogedex.SIGN_IN_URL
import com.example.dogedex.SIGN_UP_URL
import com.example.dogedex.api.dto.AddDogToUserDTO
import com.example.dogedex.api.dto.LoginDTO
import com.example.dogedex.api.dto.SignUpDTO
import com.example.dogedex.api.responses.DogListApiResponse
import com.example.dogedex.api.responses.AuthApiResponse
import com.example.dogedex.api.responses.DefaultResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService {
    @GET(GET_ALL_DOGS)
    suspend fun getAllDogs() : DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUpUser(@Body signUpDTO: SignUpDTO) : AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO) : AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse
}

object DogsApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}