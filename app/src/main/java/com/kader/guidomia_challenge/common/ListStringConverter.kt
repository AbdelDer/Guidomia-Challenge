package com.kader.guidomia_challenge.common

import androidx.room.TypeConverter

class ListStringConverter {

    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString("/")
    }

    @TypeConverter
    fun fromStringToList(string: String): List<String> {
        return string.split("/")
    }
}