package com.ezt.meal.ai.scan.database

import androidx.room.TypeConverter
import com.ezt.meal.ai.scan.model.Ingredient
import com.ezt.meal.ai.scan.model.MeasurementUnits
import com.ezt.meal.ai.scan.model.TotalNutrition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    private val gson = Gson()

    // ---------- Ingredients ----------
    @TypeConverter
    fun fromIngredientList(value: List<Ingredient>?): String =
        gson.toJson(value ?: emptyList<Ingredient>())

    @TypeConverter
    fun toIngredientList(value: String?): List<Ingredient> {
        if (value.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(value, type)
    }

    // ---------- TotalNutrition ----------
    @TypeConverter
    fun fromTotalNutrition(value: TotalNutrition?): String =
        gson.toJson(value)

    @TypeConverter
    fun toTotalNutrition(value: String?): TotalNutrition {
        if (value.isNullOrEmpty()) {
            return TotalNutrition(
                solids = 0.0,
                liquids = 0.0,
                estimatedAmount = "",
                carbs = 0.0,
                fats = 0.0,
                proteins = 0.0,
                calories = 0.0
            )
        }
        return gson.fromJson(value, TotalNutrition::class.java)
    }

    // ---------- MeasurementUnits ----------
    @TypeConverter
    fun fromMeasurementUnits(value: MeasurementUnits?): String =
        gson.toJson(value)

    @TypeConverter
    fun toMeasurementUnits(value: String?): MeasurementUnits {
        if (value.isNullOrEmpty()) return MeasurementUnits()
        return gson.fromJson(value, MeasurementUnits::class.java)
    }
}
