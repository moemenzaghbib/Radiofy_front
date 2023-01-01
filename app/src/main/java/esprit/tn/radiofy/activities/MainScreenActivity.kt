package esprit.tn.radiofy.activities



import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle

import esprit.tn.radiofy.R

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import esprit.tn.radiofy.databinding.ActivityMainScreenBinding
import esprit.tn.radiofy.fragments.Home
import com.google.android.material.bottomappbar.BottomAppBar


class MainScreenActivity : AppCompatActivity() {
    private  var appbark : BottomAppBar? = null

    lateinit var login: TextView
    lateinit var pwd: TextView
    lateinit var logout: Button
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var email_view_menu: TextView
    lateinit var name_view_menu: TextView
    lateinit var posts_fragment: FragmentContainerView



    private lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_screen)
        val bundle: Bundle? = intent.extras
        val email = intent.getStringExtra("email").toString()
//        val  viewModel by lazy {  /*ViewModelProvider(this).get(EventViewModel::class.java)*/
//            val intent = Intent(this@MainScreenActivity, EventViewModel::class.java).apply {
//                putExtra("email",email.toString())
//                Log.e("hamma ytesti lehne",email.toString())
//
//            }
//            startActivity(intent)
//        }

        val firstname = bundle?.get("firstname")
        Log.e("email", email.toString())
        val lastname = bundle?.get("lastname")
        appbark = findViewById(R.id.bottomAppBar2)
        changeFragment(Home.newInstance(email.toString()),"")





        appbark!!.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_profile -> {

                    startActivity(Intent(this, EditProfile::class.java).apply {
                        putExtra("email" ,  email.toString())
                    })

                    true
                }
                R.id.music -> {
                    startActivity(Intent(this, MusicActivity ::class.java).apply {
//                        putExtra("email" ,  email.toString())
                    })
                    true
                }
                R.id.radio -> {
                    startActivity(Intent(this, esprit.tn.radiofy.MainActivity ::class.java).apply {
//                        putExtra("email" ,  email.toString())
                    })
                    true
                }
               R.id.chat -> {
                   val intent = Intent(this, ChatRoomsList ::class.java)
                       .putExtra("email", email.toString())
                    startActivity(intent)
//                   finish()

                   true
               }

                else -> false
           }

        }

       // viewModel.init(this)
//        viewModel.fetchData().observe(this, Observer{
//            Log.d("test","lehne")
//            Log.d("fetchData", "$it")
//        })
////        setContentView(R.layout.activity_main_screen)
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
    private fun changeFragment(fragment: Fragment, name: String) {
        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.frag_home, fragment)
                .commit()
        else {
            supportFragmentManager.beginTransaction().replace(R.id.frag_home, fragment)
                .addToBackStack(name).commit()
        }
    }

}




