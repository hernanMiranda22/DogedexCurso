package com.example.dogedex.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModules {

    @IoDispatcher
    @Provides
    fun provideIODispatcher() = Dispatchers.IO

    annotation class IoDispatcher
}