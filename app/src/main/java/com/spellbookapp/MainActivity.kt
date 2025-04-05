package com.spellbookapp.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.spellbookapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    // set up the drawerlayout and navController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    // create the page
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // display the stuff from the xml file
        setContentView(R.layout.activity_main)

        // handle when the back button is pressed
        /*onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // if currently on the favorites page or the details page
                if (navController.currentDestination?.id == R.id.favoriteRecipesFragment ||
                    navController.currentDestination?.id == R.id.detailsFragment) {
                    // Go back to RecipeFragment
                    navController.navigate(R.id.recipeFragment)
                } else {
                    finish() // Close app if already on RecipeFragment
                }
            }
        })*/

        // set up the drawer layout, toolbar, and navigation
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)

        // Set up toolbar
        setSupportActionBar(toolbar)

        // Set up navigation controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up ActionBarDrawerToggle to change the string (I think this is really just for accessibility)
        val toggle = ActionBarDrawerToggle(
            // toggle between open and close drawer
            this, drawerLayout, toolbar,
            R.string.open_drawer, R.string.close_drawer
        )

        // listen for the drawer and sync the state of the toggle
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up navigation
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)

        /*// prevent the hamburger menu icon from automatically turning into an arrow
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // if the destination is a viable page
            if (destination.id == R.id.recipeFragment || destination.id == R.id.favoriteRecipesFragment || destination.id == R.id.detailsFragment) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu) // Use my menu icon
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) // lock it so it doesn't change
            } else {
                // otherwise allow normal behavior (if needed)
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }*/

        // Handle menu item selection
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // go the the right place depending on what was clicked
            when (menuItem.itemId) {
                // go to all spells if all spells was clicked
                R.id.nav_allSpells -> {
                    navController.navigate(R.id.allSpellsFragment)
                }
                // go to prepared if prepared was clicked
                R.id.nav_preparedSpells -> {
                    navController.navigate(R.id.preparedSpellsFragment)
                }
            }
            // close the drawer once they click something
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    // override the back button
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}