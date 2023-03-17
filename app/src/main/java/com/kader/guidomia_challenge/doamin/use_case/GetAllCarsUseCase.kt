package com.kader.guidomia_challenge.doamin.use_case

import android.util.Log
import com.kader.guidomia_challenge.common.Resource
import com.kader.guidomia_challenge.doamin.model.Car
import com.kader.guidomia_challenge.doamin.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "GetAllCarsUseCase"

class GetAllCarsUseCase @Inject constructor(
    private val carRepository: CarRepository
) {
    operator fun invoke(model: String?, make: String?): Flow<Resource<List<Car>>> {
        Log.d(TAG, "invoke: method called")
        return if (model.isNullOrEmpty() && make.isNullOrEmpty()) {
            Log.d(TAG, "invoke: calling getAllCars")
            print(carRepository)
            carRepository.getAllCars()
        } else {
            Log.d(TAG, "invoke: calling getCarsBayParameters")
            carRepository.getCarsByParameters(model.orEmpty(), make.orEmpty())
        }
    }
}