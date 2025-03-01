package com.example.dogedex.di

import com.example.dogedex.machinelearning.ClassifierRepository
import com.example.dogedex.machinelearning.ClassifierTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ClassifierModule{

    @Binds
    abstract fun bindClassifierTask(
        classifierRepository: ClassifierRepository
    ) : ClassifierTask
}