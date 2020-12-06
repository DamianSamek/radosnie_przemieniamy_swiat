package com.wsiz.projekt_zespolowy.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.wsiz.projekt_zespolowy.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setupWithNavController(getNavController())
        bottomNavigationView.setOnNavigationItemReselectedListener { }
    }

    private fun getNavController(): NavController {
        val navigationHost: NavHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navigationHost.navController
    }

    fun navigateTo(navDirections: NavDirections) {
        getNavController().navigate(navDirections)
    }

    fun navigateTo(navDirections: NavDirections, extras: FragmentNavigator.Extras) {
        getNavController().navigate(navDirections, extras)
    }

    fun navigateUp() {
        getNavController().navigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val hostFragment = supportFragmentManager.fragments[0] as NavHostFragment
        for(fragment in hostFragment.childFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val hostFragment = supportFragmentManager.fragments[0] as NavHostFragment
        for(fragment in hostFragment.childFragmentManager.fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}