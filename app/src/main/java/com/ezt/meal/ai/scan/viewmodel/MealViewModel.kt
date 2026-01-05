package com.ezt.meal.ai.scan.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezt.meal.ai.scan.database.Meal
import com.ezt.meal.ai.scan.database.MealRepository
import com.ezt.meal.ai.scan.di.NutritionRequest
import com.ezt.meal.ai.scan.model.ApiResponse
import com.ezt.meal.ai.scan.model.ApiResponse.Companion.DEFAULT_RESPONSE
import com.ezt.meal.ai.scan.model.NutritionResponse
import com.ezt.meal.ai.scan.screen.camera.CameraActivity.Companion.DEFAULT_MEAL
import com.ezt.meal.ai.scan.screen.language.GlobalConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(private val mealRepository: MealRepository) :
    ViewModel() {
    val allMeals = mealRepository.getAllMeals
    val recentMeals = mealRepository.getRecentMeal


    private var _selectedMeals = MutableLiveData<List<Meal>>()
    val selectedMeals: LiveData<List<Meal>> = _selectedMeals


    private var _searchMeals = MutableLiveData<List<Meal>>()
    val searchMeals: LiveData<List<Meal>> = _searchMeals


    private var _detectedMeal = MutableLiveData<ApiResponse>()
    val detectedMeal: LiveData<ApiResponse> = _detectedMeal


    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.insertMeal(meal)
            mealRepository.deleteMeals()
        }
    }

    fun deleteMeal() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mealRepository.deleteMeals()
            }
        }
    }

    fun getMealsByName(mealName: String = "") {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _searchMeals.postValue(GlobalConstant.getSearchMeals().filter { it.dishName.contains(mealName, true) })
            }

        }
    }

    fun detectMealFromImage(savedUri: File) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val output = mealRepository.detectMealFromImage(savedUri)
                    println("detectMealFromImage: $output")
                    _detectedMeal.postValue(output)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _detectedMeal.postValue(DEFAULT_RESPONSE)
                }
            }
        }

    }

    fun getMeals() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val output = mealRepository.getAllMeal()
                    println("detectMealFromImage: $output")
                    _selectedMeals.postValue(output)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    fun getMealBetweenDates(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val output = mealRepository.getMealsInRange(startDate, endDate)
                    println("detectMealFromImage: $output")
                    _selectedMeals.postValue(output)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


}