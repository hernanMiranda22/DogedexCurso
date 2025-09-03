package com.example.dogedex.core.doglist

import com.example.dogedex.core.R
import com.example.dogedex.core.model.Dog
import com.example.dogedex.core.api.ApiResponseStatus
import com.example.dogedex.core.api.ApiService
import com.example.dogedex.core.api.dto.DogDTOMapper
import com.example.dogedex.core.api.dto.AddDogToUserDTO
import com.example.dogedex.core.di.DispatchersModules
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DogTask {
    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>>
    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any>
    suspend fun getDogByMlId(mlDogId : String): ApiResponseStatus<Dog>
    suspend fun getProbableDogs(probableDogIds : ArrayList<String>) : Flow<ApiResponseStatus<Dog>>
}

class DogRepository @Inject constructor(
    private val apiService: ApiService,
    @DispatchersModules.IoDispatcher private val dispatcher: CoroutineDispatcher
) : DogTask {

    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(dispatcher) {
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            if (allDogsListResponse is ApiResponseStatus.Error) {
                allDogsListResponse
            } else if (userDogsListResponse is ApiResponseStatus.Error) {
                userDogsListResponse
            } else if (allDogsListResponse is ApiResponseStatus.Success &&
                userDogsListResponse is ApiResponseStatus.Success) {
                ApiResponseStatus.Success(getCollectionList(allDogsListResponse.data,
                    userDogsListResponse.data))
            } else {
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>) =
        allDogList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(
                    it.id, it.index, "", "", "", "",
                    "", "", "", "", "",
                    inCollection = false
                )
            }
        }.sorted()

    private suspend fun downloadDogs():ApiResponseStatus<List<Dog>> =
        com.example.dogedex.core.api.makeNetworkCall {
            val dogListResponse = apiService.getAllDogs()
            val dogDTOList = dogListResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }

    private suspend fun getUserDogs():ApiResponseStatus<List<Dog>> =
        com.example.dogedex.core.api.makeNetworkCall {
            val dogListResponse = apiService.getUserDogs()
            val dogDTOList = dogListResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> =
        com.example.dogedex.core.api.makeNetworkCall {
            val addDogToUserDTO = AddDogToUserDTO(dogId)
            val defaultResponse = apiService.addDogToUser(addDogToUserDTO)

            if (!defaultResponse.isSuccess) {
                throw Exception(defaultResponse.message)
            }
        }

    override suspend fun getDogByMlId(mlDogId : String): ApiResponseStatus<Dog> =
        com.example.dogedex.core.api.makeNetworkCall {
            val response = apiService.getDogByMlId(mlDogId)

            if (!response.isSuccess) {
                throw Exception(response.message)
            }

            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDtoToDogDomain(response.data.dog)
        }

    override suspend fun getProbableDogs(probableDogIds: ArrayList<String>): Flow<ApiResponseStatus<Dog>> =
        flow {
            for (mlDogId in probableDogIds) {
                val dog = getDogByMlId(mlDogId)
                emit(dog)
            }
        }.flowOn(dispatcher)

}