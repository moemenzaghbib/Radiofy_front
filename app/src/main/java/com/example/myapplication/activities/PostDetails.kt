package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.WARN
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.models.EventItem
import com.example.myapplication.utils.ApiInterface
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetails : AppCompatActivity() {
    lateinit var titledetaillPost : TextView
    lateinit var imagedetailPost : ImageView
    lateinit var descdetailPost : TextView
    lateinit var datedetailPost : TextView
    val apiInterface = ApiInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        val title = intent.getStringExtra("title")
        titledetaillPost = findViewById(R.id.titledetaillPost)
        imagedetailPost = findViewById(R.id.imagedetailPost)
        descdetailPost = findViewById(R.id.descdetailPost)
        datedetailPost = findViewById(R.id.datedetailPost)


        val map: HashMap<String, String> = HashMap()
        map["title"] = title.toString()
        println(map)
        apiInterface.getPost(map).enqueue(object: Callback<EventItem> {
            override fun onResponse(call: Call<EventItem>, response: Response<EventItem>) {
                val post = response.body()
                println(post)
                if (post!=null){
                    titledetaillPost.text=post.title
                    descdetailPost.text=post.desc
                    datedetailPost.text=post.date
                    Picasso.get().load(post.image).into(imagedetailPost)
                }
            }

            override fun onFailure(call: Call<EventItem>, t: Throwable) {
                println(t)
                Log.e("khlet","khlet au ²")
            }

        })


    }

}