package com.skipper.androidtest_alwakalat.network

import com.skipper.androidtest_alwakalat.network.models.ApiResponse
import com.skipper.androidtest_alwakalat.network.models.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<ApiResponse>

}