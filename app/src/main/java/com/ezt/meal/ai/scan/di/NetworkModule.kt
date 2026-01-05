package com.ezt.meal.ai.scan.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ezt.meal.ai.scan.BuildConfig
import com.ezt.meal.ai.scan.utils.Common
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext appContext: Context,
        authentication: MealAuthentication
    ): OkHttpClient {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .apply {
                        val accessToken = Common.getAccessToken(appContext)
                        Log.d("TAG", "provideOkHttpClient: ${accessToken}")
                        if (accessToken.isNullOrEmpty()) {
                            addHeader("Authorization", "Bearer $accessToken")
                        }
                    }
                    .build()
                it.proceed(request)
            }
            .apply {
                if (BuildConfig.DEBUG) {
                    this.addInterceptor(
                        ChuckerInterceptor.Builder(appContext)
                            .collector(ChuckerCollector(appContext))
                            .maxContentLength(250000L)
                            .redactHeaders(emptySet())
                            .alwaysReadResponseBody(false)
                            .build()
                    )
                }
            }
            .authenticator(authentication)
            .build()
        return client
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://meal-plan.eztechglobal.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @RefreshClient
    fun provideRefreshRetrofit(): ApiService =
        Retrofit.Builder()
            .baseUrl("https://meal-plan.eztechglobal.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build()
            .create(ApiService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshClient