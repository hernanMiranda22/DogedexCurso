package com.example.dogedex.core.api.dto

import com.google.gson.annotations.SerializedName

data class DogDTO(
    val id: Long,
    val index: Int,
    @SerializedName("name_en") val name: String,
    @SerializedName("dog_type") val type: String,
    @SerializedName("height_female") val heightFemale: String,
    @SerializedName("height_male") val heightMale: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("life_expectancy") val lifeExpectancy: String,
    val temperament: String,
    @SerializedName("weight_female") val weightFemale: String,
    @SerializedName("weight_male") val weightMale: String
)