package com.wsiz.projekt_zespolowy.data.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "RadosniePrzemieniamySwiatSharedPreferences"
        private const val TOKEN_KEY = "token"
        private const val USER_ID_KEY = "user_id"
        private const val USER_NAME_KEY = "username"

        const val MISSING_VALUE = -1
    }

    private inline fun <T> sp(func: (SharedPreferences) -> T): T {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).apply {
            return func(this)
        }
    }

    private fun putString(key: String, value: String) {
        sp { it.edit().putString(key, value).apply() }
    }

    private fun getString(key: String): String {
        return sp { it.getString(key, "") ?: "" }
    }

    private fun putInt(key: String, value: Int) {
        sp { it.edit().putInt(key, value).apply() }
    }

    private fun getInt(key: String): Int {
        return sp { it.getInt(key, MISSING_VALUE) }
    }

    fun putToken(token: String) {
        putString(TOKEN_KEY, token)
    }

    fun getToken(): String {
        return getString(TOKEN_KEY)
    }

    fun putUserId(id: Int) {
        putInt(USER_ID_KEY, id)
    }

    fun getUserId(): Int {
        return getInt(USER_ID_KEY)
    }

    fun putUserName(name: String) {
        putString(USER_NAME_KEY, name)
    }

    fun getUserName(): String {
        return getString(USER_NAME_KEY)
    }
}