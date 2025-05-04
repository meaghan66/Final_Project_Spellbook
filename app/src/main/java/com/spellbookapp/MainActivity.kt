package com.spellbookapp.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.spellbookapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

// Main Activity for hosting the fragments and drawer navigation
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    // On create instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up drawer layout, toolbar, and navigation
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)

        // Handle the insets
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val layoutParams = v.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = systemBars.top
            v.layoutParams = layoutParams

            insets
        }
        ViewCompat.requestApplyInsets(toolbar)


        setSupportActionBar(toolbar)

        // Setup the navigation fragment and controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the drawer toggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Connect the navigation drawer selection
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)

        // Handle navigation selection
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // Handle all spells page selection
                R.id.nav_allSpells -> {
                    if (navController.currentDestination?.id != R.id.allSpellsFragment) {
                        navController.navigate(R.id.allSpellsFragment)
                    }
                }
                // Handle prepared spells page selection
                R.id.nav_preparedSpells -> {
                    if (navController.currentDestination?.id != R.id.preparedSpellsFragment) {
                        navController.navigate(R.id.preparedSpellsFragment)
                    }
                }
            }
            // Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Back button handling
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Go back if in the spell detail fragment and the back button is pressed
                if (navController.currentDestination?.id == R.id.spellDetailFragment) {
                    navController.navigateUp()
                } else {
                    finish()
                }
            }
        })
    }

    // Back button navigation to return to the previous page
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
