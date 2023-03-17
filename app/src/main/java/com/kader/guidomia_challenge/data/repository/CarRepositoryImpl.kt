package com.kader.guidomia_challenge.data.repository

import android.content.res.Resources
import android.database.sqlite.SQLiteException
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

    override fun getAllMakeExisting(): Flow<Resource<List<String>>> = flow {
        Log.d(TAG, "getAllMakeExisting: Method called")
        emit(Resource.Loading())
        try {
            val makeList = getCarsFromLocalJson().map { it.make }.distinct().toMutableList()
            Log.d(TAG, "getAllMakeExisting: Retrieved list of all makes: $makeList")
            emit(Resource.Success(makeList))
        } catch (e: SQLiteException) {
            Log.e(TAG, "getAllMakeExisting: Error accessing Room database", e)
            emit(Resource.Error("Error accessing Room database"))
        } catch (e: Exception) {
            Log.e(TAG, "getAllMakeExisting: Unknown error occurred", e)
            emit(Resource.Error("Unknown error occurred"))
        }
    }

    override fun getAllModelsExisting(): Flow<Resource<List<String>>> = flow {
        Log.d(TAG, "getAllModelsExisting: Function called")
        emit(Resource.Loading())
        try {
            val modelList = getCarsFromLocalJson().map { it.model }.distinct().toMutableList()
            emit(Resource.Success(modelList))
            Log.d(TAG, "getAllModelsExisting: Retrieved list of all makes: $modelList")
        } catch (e: SQLiteException) {
            Log.e(TAG, "getAllModelsExisting: Error accessing Room database", e)
            emit(Resource.Error("Error accessing Room database"))
        } catch (e: Exception) {
            Log.e(TAG, "getAllModelsExisting: Unknown error occurred", e)
            emit(Resource.Error("Unknown error occurred"))
        }
    }

    private fun getCarsFromLocalJson(): List<CarDto> {
        Log.d(TAG, "getCarsFromLocalJson: Function called")
        val jsonFile =
            resources.openRawResource(R.raw.car_list).bufferedReader().use { it.readText() }
        val data = mAdapter.fromJson(jsonFile) ?: emptyList()
        return data
    }

    override fun getCarsByParameters(model: String, make: String): Flow<Resource<List<Car>>> =
        flow {
            emit(Resource.Loading())
            try {
                val listOfCars = getCarsFromLocalJson().filter {
                    it.model == model || it.make ==
                            make
                }.map { it.toCar() }
                emit(Resource.Success(listOfCars))
                Log.d(
                    TAG,
                    "getCarsByParameters: Retrieved ${listOfCars.size} cars from Room database"
                )
            } catch (e: SQLiteException) {
                Log.e(TAG, "getCarsByParameters: Error accessing Room database: ${e.message}", e)
                emit(Resource.Error("Error accessing Room database"))
            } catch (e: Exception) {
                Log.e(TAG, "getCarsByParameters: Unknown error occurred: ${e.message}", e)
                emit(Resource.Error("Unknown error occurred"))
            }
        }
}