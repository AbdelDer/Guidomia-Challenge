package com.kader.guidomia_challenge.data.repository

import android.content.res.Resources
import android.util.Log
import com.kader.guidomia_challenge.R
import com.kader.guidomia_challenge.common.Resource
import com.kader.guidomia_challenge.data.local.dto.CarDto
import com.kader.guidomia_challenge.doamin.model.Car
import com.kader.guidomia_challenge.doamin.repository.CarRepository
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


private const val TAG = "CarRepositoryImpl"

class CarRepositoryImpl @Inject constructor(
    private val mAdapter: JsonAdapter<List<CarDto>>,
    private val resources: Resources,
) : CarRepository {

    override fun getAllCars(): Flow<Resource<List<Car>>> = flow {
        emit(Resource.Loading())
        try {
            val allCars = getCarsFromLocalJson().map {
                it.copy(
                    prosList = it.prosList?.filter { it.isNotEmpty() },
                    consList = it.consList?.filter { it.isNotEmpty() }
                )
            }.map {
                it.toCar()
            }.map {
                it.setImage()
                it
            }
            emit(Resource.Success(allCars))
            Log.d(TAG, "getAllCars: Retrieved ${allCars.size} cars from Room database")
        } catch (e: IOException) {
            emit(Resource.Error("Error reading local file"))
            Log.e(TAG, "getAllCars: Error reading local file: ${e.message}")
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error occurred"))
            Log.e(TAG, "getAllCars: Unknown error occurred: ${e.message}")
        }
    }

    private fun getCarsFromLocalJson(): List<CarDto> {
        Log.d(TAG, "getCarsFromLocalJson: Function called")
        val jsonFile =
            resources.openRawResource(R.raw.car_list).bufferedReader().use { it.readText() }
        val data = mAdapter.fromJson(jsonFile) ?: emptyList()
        return data
    }
}