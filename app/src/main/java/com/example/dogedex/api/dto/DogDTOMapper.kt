package com.example.dogedex.api.dto

import com.example.dogedex.model.Dog

class DogDTOMapper {
    private fun fromDogDtoToDogDomain(dogDTO: DogDTO): Dog {
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

    fun fromDogDTOListToDogDomainList(dogDTOList : List<DogDTO>) : List<Dog> {
        return dogDTOList.map { fromDogDtoToDogDomain(it) }
    }
}