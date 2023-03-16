package com.kader.guidomia_challenge.common

import java.text.DecimalFormat

fun Double.getPriceToDisplay(): String {
    val formatter = DecimalFormat("#,###.#")
    val formatted = formatter.format(this)
    return when {
        this >= 1000000 -> "${formatted.substring(0, formatted.length - 2)}M"
        this >= 1000 -> "${formatted.substring(0, formatted.length - 2)}K"
        else -> formatted
    }
}