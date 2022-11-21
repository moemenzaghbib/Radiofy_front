package com.example.myapplication.activities



import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle

import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFavouriteBinding
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodels.EventViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.myapplication.databinding.ActivityMainScreenBinding


class MainScreenActivity : AppCompatActivity() {
    lateinit var login: TextView
    lateinit var pwd: TextView
    lateinit var logout: Button
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var email_view_menu: TextView
    lateinit var name_view_menu: TextView


    private lateinit var binding: ActivityMainScreenBinding
    private val  viewModel by lazy {  ViewModelProvider(this).get(EventViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_screen)
        val bundle: Bundle? = intent.extras
        val email = bundle?.get("email")
        val firstname = bundle?.get("firstname")
        Log.e("email", email.toString())
        val lastname = bundle?.get("lastname")


        viewModel.init(this)
        viewModel.fetchData().observe(this, Observer{
            Log.d("test","lehne")
            Log.d("fetchData", "$it")
        })
//        setContentView(R.layout.activity_main_screen)
//        Thread {
//            /*
//        // Run operation here
//        */val doc: Document = Jsoup.connect("https://www.shemsfm.net/fr/actualites/actualites-tunisie-news").get()
//            Log.e("title",doc.title())
//            val allInfos = doc.getElementsByClass("col-xs-12 col-sm-12 col-md-9");
//            val newsHeadlines: Elements = doc.select("#mp-itn b a")
//
//            // After getting the result
//            runOnUiThread {
//                for (headline in newsHeadlines) {
//                    System.out.println(headline.attr("title"))
//                    System.out.println(headline.absUrl("href"))
//                }   // Post the result to the main thread
//            }
//        }.start()

    }
}




