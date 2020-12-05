package com.wsiz.projekt_zespolowy.data.shared_preferences

import android.content.Context
import javax.inject.Inject

class SharedPreferences @Inject constructor() {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "RadosniePrzemieniamySwiatSharedPreferences"
        private const val TOKEN_KEY = "token"
    }


    fun putToken(context: Context, token: String) {
        val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        sp.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(context: Context): String {
        val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sp.getString(TOKEN_KEY, "") ?: ""
    }
}