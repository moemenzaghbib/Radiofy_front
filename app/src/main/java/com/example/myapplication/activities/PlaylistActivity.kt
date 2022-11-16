package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class PlaylistActivity: AppCompatActivity() {

        lateinit var imagebtn: ImageButton
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_playlist)
            imagebtn = findViewById<ImageButton>(R.id.backbtn2)

            imagebtn.setOnClickListener {
                val intent = Intent(this@PlaylistActivity, MusicPlayer::class.java)
                startActivity(intent)

            }
        }
}