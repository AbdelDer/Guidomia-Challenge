package com.kader.guidomia_challenge.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.kader.guidomia_challenge.data.local.GuidomiaChallengeDatabase
import com.kader.guidomia_challenge.data.local.dao.CarDao
import com.kader.guidomia_challenge.data.local.dto.CarDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GuidomiaModule {

    @Singleton
    @Provides
    fun provideMoshiAdapterForCar(): JsonAdapter<List<CarDto>> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listType = Types.newParameterizedType(List::class.java, CarDto::class.java)
        return moshi.adapter(listType)
    }

    @Provides
    @Singleton
    fun provideAppResource(@ApplicationContext context: Context): Resources {
        return context.resources
    }


    @Singleton
    @Provides
    fun provideGuidomiaDatabase(application: Application): GuidomiaChallengeDatabase {
        return Room.databaseBuilder(application, GuidomiaChallengeDatabase::class.java, "guidomia_db")
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideCarDao(db: GuidomiaChallengeDatabase): CarDao {
        return db.carDao
    }
}