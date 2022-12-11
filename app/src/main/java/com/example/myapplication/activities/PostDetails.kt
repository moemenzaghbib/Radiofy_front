package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.WARN
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.models.EventItem
import com.example.myapplication.models.data
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
    lateinit var like_button : ImageView
    var cliked : Boolean = false
    lateinit var likes_count : TextView

    val apiInterface = ApiInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        val title = intent.getStringExtra("title")
        titledetaillPost = findViewById(R.id.titledetaillPost)
        imagedetailPost = findViewById(R.id.imagedetailPost)
        descdetailPost = findViewById(R.id.descdetailPost)
        datedetailPost = findViewById(R.id.datedetailPost)
        like_button = findViewById(R.id.like_button)
        likes_count = findViewById(R.id.likes_count)

        val map: HashMap<String, String> = HashMap()
        map["title"] = title.toString()
        val bundle: Bundle? = intent.extras
        val email = intent.getStringExtra("email").toString()
        println("ya moemen ija chouf lehne ken mawjouda wala la \n"+email)

        val maplike: HashMap<String, String> = HashMap()
        maplike["title"]= title.toString()
        maplike["email"]= email.toString()

        apiInterface.getPost(map).enqueue(object: Callback<EventItem> {
            override fun onResponse(call: Call<EventItem>, response: Response<EventItem>) {
                val post = response.body()
                println(post)
                if (post!=null){
                    titledetaillPost.text=post.title
                    descdetailPost.text=post.desc
                    datedetailPost.text=post.date
                    likes_count.text=post.likes
                    Picasso.get().load(post.image).into(imagedetailPost)
                    apiInterface.checkLikeUser(maplike).enqueue(object: Callback<data>{
                        override fun onResponse(call: Call<data>, response: Response<data>) {
                            var liked = response.body()
                            if(liked!=null){
                                println(liked.value)
                                if(liked.value == "true"){

                                    cliked=true;
                                    like_button.setImageResource(R.drawable.black_like_icon_png_13)
                                }
                            }
                        }

                        override fun onFailure(call: Call<data>, t: Throwable) {
                            Log.e("hamma","ya hamma rahi khlet fil ligne 66")
                        }

                    })
                }
            }

            override fun onFailure(call: Call<EventItem>, t: Throwable) {
                println(t)
                Log.e("khlet","khlet au ²")
            }

        })
        like_button.setOnClickListener{
//            println(R.color.like_false)
//            println(like_button.imageTintList)

            if(!cliked){
            like_button.setImageResource(R.drawable.black_like_icon_png_13)



                apiInterface.AddLikePost(maplike).enqueue(object: Callback<data> {
                    override fun onResponse(call: Call<data>, response: Response<data>) {
                        var new_like = response.body()
                        if(new_like!=null){
                            println("hethi respone te3 el like add \n"+new_like.value)
                            likes_count.text = new_like.value
                        }
                    }

                    override fun onFailure(call: Call<data>, t: Throwable) {
                        Log.e("ya hamma rahi","KHLEEEEEET ")
                    }
                })
            cliked = true}
            else if(cliked){
                like_button.setImageResource(R.drawable.black_like_icon_png_12)
                apiInterface.RemoveLikePost(maplike).enqueue(object: Callback<data> {
                    override fun onResponse(call: Call<data>, response: Response<data>) {
                        var new_like = response.body()
                        if(new_like!=null){
                            println("hethi respone te3 el like remove \n"+new_like.value)
                            likes_count.text = new_like.value
                        }
                    }

                    override fun onFailure(call: Call<data>, t: Throwable) {
                        Log.e("ya hamma rahi","KHLEEEEEET ")
                    }
                })
                println(cliked)
            cliked = false}

        }


    }

}