package com.skipper.androidtest_alwakalat.repository


import com.skipper.androidtest_alwakalat.network.models.ApiResponse
import com.skipper.androidtest_alwakalat.network.models.LoginRequest
import com.skipper.androidtest_alwakalat.network.ApiService
import com.skipper.androidtest_alwakalat.network.RetrofitClient
import retrofit2.Callback

class LoginRepository{
    private val apiService = RetrofitClient.createService(ApiService::class.java)

    fun loginUser(loginRequest: LoginRequest, callback: Callback<ApiResponse>) {
        val call = apiService.login(loginRequest)
        call.enqueue(callback)
    }

}