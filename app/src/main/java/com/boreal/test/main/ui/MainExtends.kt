package com.boreal.test.main.ui

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.boreal.test.R

internal fun MainActivity.initElements() {
    binding.apply {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigation_base) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)
    }
}