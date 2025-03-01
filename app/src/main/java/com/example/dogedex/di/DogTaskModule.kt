package com.example.dogedex.di

import com.example.dogedex.doglist.DogRepository
import com.example.dogedex.doglist.DogTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DogTaskModule {

    @Binds
    abstract fun bindAnalyticsService(
        dogRepository: DogRepository
    ): DogTask
}