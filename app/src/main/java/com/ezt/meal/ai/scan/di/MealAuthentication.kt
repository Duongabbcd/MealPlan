package com.ezt.meal.ai.scan.di

import android.content.Context
import android.util.Log
import com.ezt.meal.ai.scan.utils.AESEncryption
import com.ezt.meal.ai.scan.utils.AESEncryption.getDeviceID
import com.ezt.meal.ai.scan.utils.Common
import com.ezt.meal.ai.scan.utils.DeviceData
import com.ezt.meal.ai.scan.utils.DeviceRequestToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

@Singleton
class MealAuthentication @Inject constructor(
    @RefreshClient private val noteServer: ApiService,
    @ApplicationContext private val context: Context
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) {
            return null
        }
        synchronized(this) {
            return try {
                val data = DeviceData()
                try {
                    data.apply {
                        id = 0
                        client_id = getDeviceID(context)
                        platform = "android"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Log.d("TAG", "authenticate data: 1}")
                val deviceRequest = DeviceRequestToken(encodedPayload = AESEncryption.encrypt(data))
                Log.d("TAG", "authenticate: ${deviceRequest}")
                val refreshResponse = noteServer.createDeviceToken(deviceRequest).execute()
                if (refreshResponse.isSuccessful && refreshResponse.body() != null) {
                    val newToken = refreshResponse.body()!!.data?.accessToken
                    if (!newToken.isNullOrEmpty()) {
                        Common.setAccessToken(context,newToken )
                        Log.d("TAG", "authenticate data: ${newToken}")
                        response.request.newBuilder()
                            .addHeader("Authorization", "Bearer $newToken")
                            .build()
                    } else {
                        Log.d("TAG", "authenticate: err")
                        null
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.d("TAG", "authenticate: err $e")
                e.printStackTrace()
                null
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}