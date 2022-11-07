package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.R

class MainScreenActivity : AppCompatActivity() {
    lateinit var login : TextView
    lateinit var pwd : TextView
    lateinit var logout : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        login = findViewById(R.id.email_show)
        pwd = findViewById(R.id.password_show)
        logout = findViewById(R.id.LogOut_Button)



        logout.setOnClickListener() {
            getSharedPreferences("PREF_NAME", MODE_PRIVATE).edit().clear().apply()
            finish()
        }
    }


}