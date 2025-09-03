package com.example.dogedex.core.api.dto

import com.example.dogedex.core.model.Dog

class DogDTOMapper {
    fun fromDogDtoToDogDomain(dogDTO: com.example.dogedex.core.api.dto.DogDTO): Dog {
        return Dog(
            id = dogDTO.id,
            index = dogDTO.index,
            name = dogDTO.name,
            type = dogDTO.type,
            heightFemale = dogDTO.heightFemale,
            heightMale = dogDTO.heightMale,
            imageUrl = dogDTO.imageUrl,
            lifeExpectancy = dogDTO.lifeExpectancy,
            temperament = dogDTO.temperament,
            weightFemale = dogDTO.weightFemale,
            weightMale = dogDTO.weightMale
        )
    }

    fun fromDogDTOListToDogDomainList(dogDTOList: List<com.example.dogedex.core.api.dto.DogDTO>): List<Dog> {
        return dogDTOList.map { fromDogDtoToDogDomain(it) }
    }
}