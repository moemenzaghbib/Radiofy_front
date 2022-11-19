package com.example.myapplication.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.R

class MusicPlayer: AppCompatActivity()  {
    lateinit var shufflebtn: Button
    lateinit var favouritebtn: Button
    lateinit var palylistbtn: Button
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestRuntimePermission()
        setContentView(R.layout.activity_music_player)
        supportActionBar?.hide()

        shufflebtn = findViewById<Button>(R.id.shufflebtn)
        favouritebtn = findViewById<Button>(R.id.favouritebtn)
        palylistbtn = findViewById<Button>(R.id.palylistbtn)

//        var drawer = findViewById<DrawerLayout>(R.id.drawer)
//        toggle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
//        toggle.syncState()
        shufflebtn.setOnClickListener {
            val intent = Intent(this@MusicPlayer, PlaylistActivity::class.java)
            startActivity(intent)
        }
        favouritebtn.setOnClickListener {
            val intent = Intent(this@MusicPlayer, FavouriteActivity::class.java)
            startActivity(intent)
        }
        palylistbtn.setOnClickListener {
            val intent = Intent(this@MusicPlayer, PlaylistActivity::class.java)
            startActivity(intent)
        }
    }
    // for requesting permission
    private fun requestRuntimePermission(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),13)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 13){
            if (grantResults.isNotEmpty() &&grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),13)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}