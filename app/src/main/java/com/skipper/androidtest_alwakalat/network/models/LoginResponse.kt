package com.skipper.androidtest_alwakalat.network.models

import androidx.annotation.Keep


@Keep
data class LoginRequest(
    val emailAddress: String,
    val password: String,
)

@Keep
data class ApiResponse(
    val success: User?,
    val error: Error?,
)

@Keep
data class User(
    val firstName: String,
    val lastName: String,
    val accountStatus: String,
    val token: String,
    val emailAddress: String
)
@Keep
data class Error(
    val status: Int,
    val error: String,
    val message: String
)