package com.akameko.testforindeed.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akameko.testforindeed.R
import com.akameko.testforindeed.view.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoginActivity()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                    .add(R.id.container, MainFragment.newInstance())
                    .commitNow()

        }

    }
    private fun showLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


//    fun showDetails() {
//        val detailFragment = DetailFragment()
//        val fm = supportFragmentManager
//        fm.beginTransaction()
//                .replace(R.id.container, detailFragment)
//                .addToBackStack("")
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit()
//    }
}