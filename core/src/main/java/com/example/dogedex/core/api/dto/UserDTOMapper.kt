package com.example.dogedex.core.api.dto

import com.example.dogedex.core.model.User

class UserDTOMapper {
    fun fromUserDtoToUserDomain(userDTO: UserDTO): User =
        User(
            userDTO.id,
            userDTO.email,
            userDTO.authenticationToken
        )
}