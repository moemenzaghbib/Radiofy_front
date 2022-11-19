package com.example.myapplication.activities


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


class MainScreenActivity : AppCompatActivity() {
    lateinit var login: TextView
    lateinit var pwd: TextView
    lateinit var logout: Button
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var email_view_menu: TextView
    lateinit var name_view_menu: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = intent.extras
        val email = bundle?.get("email")
        val firstname = bundle?.get("firstname")
        Log.e("email", email.toString())
        val lastname = bundle?.get("lastname")
        setContentView(R.layout.activity_main_screen)
        Thread {
            /*
        // Run operation here
        */val doc: Document = Jsoup.connect("https://en.wikipedia.org/").get()
            Log.e("title",doc.title())
            val newsHeadlines: Elements = doc.select("#mp-itn b a")

            // After getting the result
            runOnUiThread {
                for (headline in newsHeadlines) {
                    System.out.println(headline.attr("title"))
                    System.out.println(headline.absUrl("href"))
                }   // Post the result to the main thread
            }
        }.start()











    }



}




