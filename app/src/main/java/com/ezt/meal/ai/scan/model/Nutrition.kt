package com.ezt.meal.ai.scan.model

import com.ezt.meal.ai.scan.screen.camera.CameraActivity
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val data: DataWrapper
) {
    companion object {
        val DEFAULT_RESPONSE = ApiResponse(
            data = DataWrapper(
                data = CameraActivity.DEFAULT_MEAL,
                queue = QueueResponse("", "", 0, 0, 0, listOf<QueueInput>(), "", "", 0, 0.0, 0.0),
                time = TimeInfo(0.0, 0.0)
            )
        )

        val DEFAULT_RESPONSE_2 = ApiResponse(
            data = DataWrapper(
                data = CameraActivity.DEFAULT_MEAL_2,
                queue = QueueResponse("", "", 0, 0, 0, listOf<QueueInput>(), "", "", 0, 0.0, 0.0),
                time = TimeInfo(0.0, 0.0)
            )
        )
    }
}

data class DataWrapper(
    val data: NutritionResponse, // <- matches the inner "data" object
    val queue: QueueResponse,
    val time: TimeInfo
)

data class QueueResponse(
    @SerializedName("client_ip")
    val clientIp: String,

    val country: String,

    val type: Int,

    val status: Int,

    @SerializedName("device_id")
    val deviceId: Int,

    val input: List<QueueInput>,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("created_at")
    val createdAt: String,

    val id: Int,

    @SerializedName("process_time")
    val processTime: Double,

    @SerializedName("wait_time")
    val waitTime: Double
)

data class QueueInput(
    val type: String,
    val disk: String,
    val path: String,
    val url: String
)

data class TimeInfo(
    @SerializedName("time_get_link")
    val timeGetLink: Double,

    @SerializedName("time_convert")
    val timeConvert: Double
)

data class NutritionResponse(
    @SerializedName("dish_name")
    val dishName: String,

    val ingredients: List<Ingredient>,

    @SerializedName("total_nutrition")
    val totalNutrition: TotalNutrition,

    @SerializedName("healthy_score")
    val healthyScore: Int,

    @SerializedName("measurement_units")
    val measurementUnits: MeasurementUnits,

    @SerializedName("processing_time")
    val processingTime: Double,

    val status: String,
    val message: String
)

data class Ingredient(
    @SerializedName("Food Name")
    val foodName: String,

    @SerializedName("Estimated Amount")
    val estimatedAmount: Double,

    @SerializedName("Unit")
    val unit: String,

    @SerializedName("Carbs")
    val carbs: Double,

    @SerializedName("Fats")
    val fats: Double,

    @SerializedName("Proteins")
    val proteins: Double,

    @SerializedName("Calories")
    val calories: Double
)

data class TotalNutrition(
    @SerializedName("Solids")
    val solids: Double,

    @SerializedName("Liquids")
    val liquids: Double,

    @SerializedName("Estimated Amount")
    val estimatedAmount: String,

    @SerializedName("Carbs")
    val carbs: Double,

    @SerializedName("Fats")
    val fats: Double,

    @SerializedName("Proteins")
    val proteins: Double,

    @SerializedName("Calories")
    val calories: Double
)

data class MeasurementUnits(
    val solids: String = "g",
    val liquids: String = "ml",
    val carbs: String = "g",
    val fats: String = "g",
    val proteins: String = "g",
    val calories: String = "kcal"
)