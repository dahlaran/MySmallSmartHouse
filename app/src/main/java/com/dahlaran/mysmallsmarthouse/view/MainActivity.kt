package com.dahlaran.mysmallsmarthouse.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dahlaran.mysmallsmarthouse.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationController =
            supportFragmentManager.findFragmentById(R.id.navigationHostFragment) as NavHostFragment

        mainBottomNavigation.setupWithNavController(navigationController.navController)
    }
}