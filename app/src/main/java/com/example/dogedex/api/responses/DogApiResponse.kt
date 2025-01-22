package com.example.dogedex.api.responses

import com.example.dogedex.api.dto.DogDTO
import com.google.gson.annotations.SerializedName

data class DogApiResponse(
    val message: String,
    @SerializedName("is_success") val isSuccess: Boolean,
    val data: DogResponse,
)
