package com.kader.guidomia_challenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kader.guidomia_challenge.common.ListStringConverter
import com.kader.guidomia_challenge.data.local.dao.CarDao
import com.kader.guidomia_challenge.data.local.entity.CarEntity

@Database(
    entities = [CarEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringConverter::class)
abstract class GuidomiaChallengeDatabase : RoomDatabase() {
    abstract val carDao: CarDao
}