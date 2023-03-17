package com.kader.guidomia_challenge.doamin.repository


import com.kader.guidomia_challenge.common.Resource
import com.kader.guidomia_challenge.doamin.model.Car
import kotlinx.coroutines.flow.Flow


interface CarRepository {
    fun getAllCars(): Flow<Resource<List<Car>>>
    fun getAllMakeExisting(): Flow<Resource<List<String>>>
    fun getAllModelsExisting(): Flow<Resource<List<String>>>
    fun getCarsByParameters(model: String, make: String): Flow<Resource<List<Car>>>
}