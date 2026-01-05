package com.ezt.meal.ai.scan.di

import android.app.Application
import androidx.room.Room
import com.ezt.meal.ai.scan.database.MealDao
import com.ezt.meal.ai.scan.database.MealDatabase
import com.ezt.meal.ai.scan.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideMealDB(application: Application): MealDatabase {
        return Room.databaseBuilder(
            application,
            MealDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    @Provides
    @Singleton
    fun provideMealDao(mealDB: MealDatabase): MealDao {
        return mealDB.mealDao()
    }


}