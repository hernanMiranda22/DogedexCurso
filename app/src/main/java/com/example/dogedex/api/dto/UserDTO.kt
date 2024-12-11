package com.example.dogedex.api.dto

import com.google.gson.annotations.SerializedName

data class UserDTO (
    val id : Long,
    val email : String,
    @SerializedName("authentication_token") val authenticationToken : String
)