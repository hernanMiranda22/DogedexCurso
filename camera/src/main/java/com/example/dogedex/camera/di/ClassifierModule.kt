package com.example.dogedex.camera.di

import com.example.dogedex.camera.machinelearning.ClassifierRepository
import com.example.dogedex.camera.machinelearning.ClassifierTask
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