package com.wsiz.projekt_zespolowy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setupWithNavController(getNavController())
    }

    fun getNavController(): NavController {
        val navigationHost: NavHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navigationHost.navController
    }
}