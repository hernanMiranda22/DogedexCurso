package com.example.dogedex.core.api.responses

import com.google.gson.annotations.SerializedName

data class DogApiResponse(
    val message: String,
    @SerializedName("is_success") val isSuccess: Boolean,
    val data: DogResponse,
)
