package com.ezt.meal.ai.scan.database

import androidx.lifecycle.LiveData
import com.ezt.meal.ai.scan.di.ApiService
import com.ezt.meal.ai.scan.di.NutritionRequest
import com.ezt.meal.ai.scan.model.ApiResponse
import com.ezt.meal.ai.scan.model.NutritionResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MealRepository @Inject constructor(private val apiService: ApiService, private val mealDao: MealDao){
    val getAllMeals = mealDao.getAllMeals()
    val getRecentMeal = mealDao.getRecentMeals()

    fun insertMeal(meal: Meal){
        mealDao.insertMeal(meal)
    }

    fun getMealsInRange(startDate: Long, endDate:Long) : List<Meal> {
        return  mealDao.getMealsInRange(startDate, endDate)
    }
    fun getAllMeal() : List<Meal> {
        return  mealDao.getMeals()
    }

    suspend fun deleteMeals() {
        mealDao.deleteFailedMeals()
    }

    fun getMealsByName(mealName: String): List<Meal>{
        return mealDao.getPostsByUsername(mealName)
    }

    suspend fun detectMealFromImage(mealImage: File): ApiResponse {
        val requestBody = mealImage.asRequestBody("image/*".toMediaType())
        val path =  MultipartBody.Part.createFormData(
            name = "image",
            filename = mealImage.name,
            body = requestBody
        )
        val output = apiService.detectMealFromImage(image = path)
        return output
    }

    companion object {
        var TOKEN: String = ""
    }
}