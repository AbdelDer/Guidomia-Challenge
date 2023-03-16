package com.kader.guidomia_challenge.di

import com.kader.guidomia_challenge.data.repository.CarRepositoryImpl
import com.kader.guidomia_challenge.doamin.repository.CarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CarRepositoryModule {
    @Binds
    abstract fun bindCarRepository(impl: CarRepositoryImpl): CarRepository
}