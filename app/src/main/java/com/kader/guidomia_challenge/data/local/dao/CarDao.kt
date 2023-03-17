package com.kader.guidomia_challenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kader.guidomia_challenge.data.local.entity.CarEntity


@Dao
interface CarDao {

    @Query("SELECT * FROM CarEntity")
    fun getAllCars(): List<CarEntity>

    @Insert()
    fun insertCar(car: CarEntity)

    @Query("SELECT * FROM CarEntity WHERE model = :model OR make = :make")
    fun getCarsByParameters(model: String, make: String): List<CarEntity>

    @Query("SELECT make from CarEntity")
    fun getAllMakeExisting(): List<String>

    @Query("SELECT model from CarEntity")
    fun getAllModelsExisting(): List<String>
}