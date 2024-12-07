package com.example.dogedex.doglist

import com.example.dogedex.Dog
import com.example.dogedex.api.DogsApi.retrofitService
import com.example.dogedex.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun downloadDogs():List<Dog>{
        return withContext(Dispatchers.IO){
            val dogListResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }
}