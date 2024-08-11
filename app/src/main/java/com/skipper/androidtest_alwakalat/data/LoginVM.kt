package com.skipper.androidtest_alwakalat.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skipper.androidtest_alwakalat.network.models.ApiResponse
import com.skipper.androidtest_alwakalat.network.models.LoginRequest
import com.skipper.androidtest_alwakalat.repository.LoginRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginVM : ViewModel() {
    private val loginRepository = LoginRepository()


    fun loginUser(
        username: String,
        password: String,
        context: Context,
        onResponse: (Boolean, String) -> Unit,
    ) {
        // Set isLoading to true when the API call starts
        viewModelScope.launch {

            if (isValidCredentials(username, password)) {
                val loginRequest = LoginRequest(username, password)

                loginRepository.loginUser(loginRequest, object : Callback<ApiResponse> {
                    override fun onResponse(
                        call: Call<ApiResponse>,
                        response: Response<ApiResponse>
                    ) {
                        // Set isLoading to false when the API call completes

                        if (response.isSuccessful) {
                            val apiResponse = response.body()

                            apiResponse?.let {
                                if (response.body()?.success != null) {
                                    SecureUserPrefs.saveUser(context, response.body()?.success!!)
                                    onResponse.invoke(true, "")

                                } else {
                                    if (response.body()?.error != null) {
                                        onResponse.invoke(
                                            false,
                                            response.body()?.error?.message.toString()
                                        )
                                    }

                                }
                            }

                        } else {
                            if (response.body()?.error != null) {
                                onResponse.invoke(false, response.body()?.error?.message.toString())
                            } else {
                                onResponse.invoke(false, "Login failed")
                            }

                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        onResponse.invoke(false, "Network error")

                    }
                })
            } else {
                // Set isLoading to false if credentials are invalid
                onResponse.invoke(false, "Invalid Username Or Password")

            }
        }
    }

    fun isValidCredentials(username: String, password: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        val passwordRegex =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+={}\\[\\]|;:'\",.<>?/~]).{8,}$".toRegex()

        val isEmailValid = username.matches(emailRegex)
        val isPasswordValid = password.matches(passwordRegex)

        return isEmailValid && isPasswordValid
    }
}


data class LoginModel(
    var etTextFieldValue: String? = null,
    var etTextFieldOneValue: String? = null,
    var loginSuccess: Boolean = false,
)
