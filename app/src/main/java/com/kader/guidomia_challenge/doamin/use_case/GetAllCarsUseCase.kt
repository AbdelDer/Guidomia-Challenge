package com.kader.guidomia_challenge.doamin.use_case

import android.util.Log
import com.kader.guidomia_challenge.common.Resource
import com.kader.guidomia_challenge.doamin.model.Car
import com.kader.guidomia_challenge.doamin.repository.CarRepository
import kotlinx.coroutines.flow.Flow

private const val TAG = "GetAllCarsUseCase"

class GetAllCarsUseCase(
    private val carRepository: CarRepository
) {
    operator fun invoke(): Flow<Resource<List<Car>>> {
        Log.d(TAG, "invoke: method called")
        Log.d(TAG, "invoke: calling getAllCars")
        return carRepository.getAllCars()

    }
}