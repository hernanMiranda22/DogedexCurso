package com.example.dogedex.api.responses

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class DogListApiResponse(
    val message: String,
    @SerializedName("is_success") val isSuccess: Boolean,
    val data: DogListResponse,
)