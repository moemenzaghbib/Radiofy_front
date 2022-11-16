package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class PlayerActivity : AppCompatActivity() {

    lateinit var imagebtn: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        imagebtn = findViewById<ImageButton>(R.id.backbtn)

        imagebtn.setOnClickListener {
            val intent = Intent(this@PlayerActivity, MusicPlayer::class.java)
            startActivity(intent)

        }

    }
}