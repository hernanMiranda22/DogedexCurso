package com.example.dogedex.core.di

import com.example.dogedex.core.auth.AuthRepository
import com.example.dogedex.core.auth.AuthTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthTaskModule {

    @Binds
    abstract fun bindAuthTask(
        authRepository: AuthRepository
    ) : AuthTasks
}