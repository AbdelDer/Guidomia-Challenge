package com.kader.guidomia_challenge.data.local.dto

import com.kader.guidomia_challenge.doamin.model.Car


data class CarDto(
    val consList: List<String>?,
    val customerPrice: Double,
    val make: String,
    val marketPrice: Double,
    val model: String,
    val prosList: List<String>?,
    val rating: Int
) {
    fun toCar(): Car {
        return Car(
            rating = rating,
            model = model,
            make = make,
            consList = consList ?: listOf(),
            marketPrice = marketPrice,
            prosList = prosList ?: listOf(),
        )
    }
}