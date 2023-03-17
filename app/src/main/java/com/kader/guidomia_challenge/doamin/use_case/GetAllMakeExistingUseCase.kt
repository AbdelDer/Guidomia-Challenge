package com.kader.guidomia_challenge.doamin.use_case

import android.util.Log
import com.kader.guidomia_challenge.common.Resource
import com.kader.guidomia_challenge.doamin.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "GetAllMakeExistingUseCase"

class GetAllMakeExistingUseCase @Inject constructor(private val repository: CarRepository) {
    operator fun invoke(): Flow<Resource<List<String>>> {
        Log.d(TAG, "invoke: method called")
        return repository.getAllMakeExisting()
    }
}