package com.ezt.meal.ai.scan.di

import com.ezt.meal.ai.scan.model.ApiResponse
import com.ezt.meal.ai.scan.model.NutritionResponse
import com.ezt.meal.ai.scan.utils.DeviceRequestToken
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("/api/v1/auth/add-device")
    fun createDeviceToken(@Body request: DeviceRequestToken): Call<DeviceToken>


    @Multipart
    @POST("api/v1/food/detection")
    suspend fun detectMealFromImage(
        @Part image: MultipartBody.Part?
    ): ApiResponse

}


data class NutritionRequest(
    @SerializedName("image") val name: String
)

data class DeviceToken(
    @SerializedName("data")
    @Expose
    var data: Data? = null
)

data class Data(
    @SerializedName("access_token")
    @Expose
    var accessToken : String? = null
)