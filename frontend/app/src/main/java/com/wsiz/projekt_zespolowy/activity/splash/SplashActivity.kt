package com.wsiz.projekt_zespolowy.activity.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsiz.projekt_zespolowy.activity.login.LoginActivity
import com.wsiz.projekt_zespolowy.activity.main.MainActivity
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = sharedPreferences.getToken()
        val userId = sharedPreferences.getUserId()

        if (token.isEmpty() || userId == -1) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}