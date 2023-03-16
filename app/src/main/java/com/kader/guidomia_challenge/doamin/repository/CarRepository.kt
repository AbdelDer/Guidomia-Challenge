package com.kader.guidomia_challenge.doamin.repository


import com.kader.guidomia_challenge.common.Resource
import com.kader.guidomia_challenge.doamin.model.Car
import kotlinx.coroutines.flow.Flow


interface CarRepository {
    fun getAllCars(): Flow<Resource<List<Car>>>
}