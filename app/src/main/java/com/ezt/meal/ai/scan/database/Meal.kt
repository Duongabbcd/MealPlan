package com.ezt.meal.ai.scan.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ezt.meal.ai.scan.model.Ingredient
import com.ezt.meal.ai.scan.model.MeasurementUnits
import com.ezt.meal.ai.scan.model.TotalNutrition
import com.google.gson.annotations.SerializedName

@Entity(tableName = "meal")
data class Meal (
    @PrimaryKey(autoGenerate = true)
    val id: Int ? = null,
    val dishName: String,
    val image: String,
    val ingredients: List<Ingredient>,
    val totalNutrition: TotalNutrition,
    val healthyScore: Int,
    val measurementUnits: MeasurementUnits,
    val processingTime: Double,
    val status: String,
    val message: String,
    val date: Long,
    var isFront: Int = 0
)