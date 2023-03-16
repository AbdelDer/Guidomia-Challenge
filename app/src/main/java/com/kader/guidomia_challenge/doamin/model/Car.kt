package com.kader.guidomia_challenge.doamin.model

import com.kader.guidomia_challenge.R

class Car(
    val consList: List<String>,
    val make: String,
    val marketPrice: Double,
    val model: String,
    val prosList: List<String>,
    val rating: Int,
    var expanded: Boolean = false,
    var image: Int? = null
) {

    fun setImage() {
        when (model) {
            "GLE coupe" -> image = R.drawable.mercedez_benz_glc
            "3300i" -> image = R.drawable.bmw_330i
            "Roadster" -> image = R.drawable.alpine_roadster
            "Range Rover" -> image = R.drawable.range_rover
        }
    }
}