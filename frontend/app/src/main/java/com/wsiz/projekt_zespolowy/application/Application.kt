package com.wsiz.projekt_zespolowy.application

import android.app.Application
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application : Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sharedPreferences.putToken("eyJhbGciOiJIUzUxMiJ9.eyJVU0VSX0FVVEhPUklUWSI6InVzZXIiLCJzdWIiOiJ1c2VyMSJ9.ycxyZMrmVw7RcUItjHint2vHI7BOP_Tyv3rrTxwLs37Iy68P7_plwYlkr_E3uRmKWYFj201Bt52tygac7EzKRA")
        sharedPreferences.putUserId(2)
    }
}