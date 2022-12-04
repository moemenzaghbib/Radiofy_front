package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPlayNextBinding

class PlayNext : AppCompatActivity() {
    companion object{
        var playNextList: ArrayList<Music> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MusicActivity.currentTheme[MusicActivity.themeIndex])
        val binding = ActivityPlayNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playNextRV.setHasFixedSize(true)
        binding.playNextRV.setItemViewCacheSize(13)
        binding.playNextRV.layoutManager = GridLayoutManager(this, 4)
        //   binding.playNextRV.adapter = FavouriteAdapter(this, playNextList, playNext = true)

        if(playNextList.isNotEmpty())
            binding.instructionPN.visibility = View.GONE

        binding.backBtnPN.setOnClickListener {
            finish()
        }
    }
}