package com.akameko.testforindeed.view.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.akameko.testforindeed.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                    .add(R.id.nav_host_fragment, MainFragment.newInstance())
                   // .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commitNow()

        }
        initNavigation()

    }

    private fun initNavigation() {
        navView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(this)
        navView.selectedItemId = R.id.navigation_main
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_favourite -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, FavouriteFragment.newInstance())
                        //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commitNow()
                return true
            }
            R.id.navigation_main -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, MainFragment.newInstance())
                       // .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commitNow()


                return true
            }

        }
        return false
    }
}