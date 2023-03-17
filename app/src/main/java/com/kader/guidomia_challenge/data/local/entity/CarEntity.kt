package com.kader.guidomia_challenge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kader.guidomia_challenge.doamin.model.Car
import com.squareup.moshi.JsonClass


@Entity
@JsonClass(generateAdapter = true)
data class CarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val make: String,
    val model: String,
    val marketPrice: Double,
    val rating: Int,
    val prosList: List<String>,
    val consList: List<String>
) {
    fun toCar(): Car {
        return Car(
            consList = consList,
            make = make,
            marketPrice = marketPrice,
            model = model,
            prosList = prosList,
            rating = rating,
        )
    }
}