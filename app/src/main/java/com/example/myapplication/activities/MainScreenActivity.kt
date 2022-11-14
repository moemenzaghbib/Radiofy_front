package com.example.myapplication.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.compose.material.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.navigation.NavigationView


class MainScreenActivity : AppCompatActivity() {
    lateinit var login: TextView
    lateinit var pwd: TextView
    lateinit var logout: Button
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var email_view_menu: TextView
    lateinit var name_view_menu: TextView


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = intent.extras
        val email = bundle?.get("email")
        val firstname = bundle?.get("firstname")
        Log.e("email", email.toString())
        val lastname = bundle?.get("lastname")
        setContentView(R.layout.activity_main_screen)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navigationView.getHeaderView(0)
        val navUsername: TextView = headerView.findViewById(R.id.email_menu_view)
        val navUserEmail: TextView = headerView.findViewById(R.id.username_menu_view)

        navUsername.text = email.toString()
        navUserEmail.text = firstname.toString()


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu_item -> Toast.makeText(
                    applicationContext,
                    " Clicked Home",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.edit_profile_menu_item -> Toast.makeText(
                    applicationContext,
                    " Edit Profile",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.setting_menu_item -> Toast.makeText(
                    applicationContext,
                    " Settings clicked",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.loguout_menu_item -> Toast.makeText(
                    applicationContext,
                    " Logging out",
                    Toast.LENGTH_SHORT
                ).show()


            }
            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return true
}

}




