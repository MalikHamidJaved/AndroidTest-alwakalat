package com.skipper.androidtest_alwakalat.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://androidtestalwakalat.free.beeceptor.com/"

    private var retrofit: Retrofit? = null


    fun createService(serviceClass: Class<ApiService>): ApiService {
        if (retrofit == null) {
            // Create an OkHttpClient with a logging interceptor
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Adjust the logging level as needed
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient) // Set the OkHttpClient with the logging interceptor
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(serviceClass)
    }
}