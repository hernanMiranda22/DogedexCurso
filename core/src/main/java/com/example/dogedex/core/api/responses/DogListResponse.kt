package com.example.dogedex.core.api.responses

import com.example.dogedex.core.api.dto.DogDTO

data class DogListResponse(val dogs : List<DogDTO>)