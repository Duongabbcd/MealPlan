package com.ezt.meal.ai.scan.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: Meal)

    @Query("SELECT * FROM meal WHERE status = :status")
    fun getAllMeals(status: String = "success"): LiveData<List<Meal>>

    @Query("SELECT * FROM meal WHERE status = :status")
    fun getMeals(status: String = "success"): List<Meal>


    @Query("SELECT * FROM meal WHERE date BETWEEN :startDate AND :endDate")
    fun getMealsInRange(startDate: Long, endDate: Long): List<Meal>

    @Query("DELETE FROM meal WHERE status != :status")
    suspend fun deleteFailedMeals(status: String = "success")

    @Query("SELECT * FROM meal ORDER BY id DESC limit 5")
    fun getRecentMeals(): LiveData<List<Meal>>


    @Query("SELECT * FROM meal WHERE dishName LIKE '%' || :name || '%' ORDER BY id")
    fun getPostsByUsername(name: String): List<Meal>



}