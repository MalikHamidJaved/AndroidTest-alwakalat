package com.skipper.androidtest_alwakalat.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.skipper.androidtest_alwakalat.network.models.User


object SecureUserPrefs {

    private const val PREFS_NAME = "secure_user_prefs"
    private const val KEY_USER = "user"

    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        // Create or retrieve the MasterKey for encryption
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveUser(context: Context, user: User) {
        val gson = Gson()
        val userJson = gson.toJson(user)
        val sharedPreferences = getEncryptedSharedPreferences(context)
        with(sharedPreferences.edit()) {
            putString(KEY_USER, userJson)
            apply()
        }
    }

    fun getUser(context: Context): User? {
        val sharedPreferences = getEncryptedSharedPreferences(context)
        val userJson = sharedPreferences.getString(KEY_USER, null)
        return if (userJson != null) {
            Gson().fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    fun clearUser(context: Context) {
        val sharedPreferences = getEncryptedSharedPreferences(context)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}