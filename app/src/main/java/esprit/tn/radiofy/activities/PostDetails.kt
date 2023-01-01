package esprit.tn.radiofy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R
import esprit.tn.radiofy.adapters.CommentAdapter
import esprit.tn.radiofy.models.Comment
import esprit.tn.radiofy.models.EventItem
import esprit.tn.radiofy.models.data
import esprit.tn.radiofy.utils.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetails : AppCompatActivity() {
    lateinit var recylcerComments: RecyclerView

    lateinit var recylcerCommentsAdapter: CommentAdapter
    lateinit var titledetaillPost : TextView
    lateinit var imagedetailPost : ImageView
    lateinit var descdetailPost : TextView
    lateinit var datedetailPost : TextView
    lateinit var like_button : ImageView
    var cliked : Boolean = false
    lateinit var likes_count : TextView
    lateinit var comment_input : TextInputEditText
    lateinit var comment_post : ImageView
    lateinit var comment_input_layout : TextInputLayout
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
        recylcerComments = findViewById(R.id.commentsRecycleView)

        comment_post = findViewById(R.id.post_comment_button)
        comment_input = findViewById(R.id.comment_input)
        comment_input_layout = findViewById(R.id.comment_input_layout)
        val email = intent.getStringExtra("email").toString()
        val map: HashMap<String, String> = HashMap()
        map["title"] = title.toString()
        println("moemen test lil map"+map)
        apiInterface.getComments(map).enqueue(object: Callback<ArrayList<data>>{
            override fun onResponse(
                call: Call<ArrayList<data>>,
                response: Response<ArrayList<data>>
            ) {
                val comments = response.body()
                if(comments!=null){
                    println("first test\n"+comments)
                    val list = mutableListOf<Comment>()

                    for(i in comments){
                        list.add(Comment(i.key.toString(),i.value.toString()))

                    }

                    recylcerCommentsAdapter = CommentAdapter(list)
                    recylcerComments.adapter = recylcerCommentsAdapter
                   recylcerComments.layoutManager = LinearLayoutManager(this@PostDetails, LinearLayoutManager.VERTICAL ,false)

                }
            }

            override fun onFailure(call: Call<ArrayList<data>>, t: Throwable) {
                println("khlet commnttr")
            }


        })


        val bundle: Bundle? = intent.extras

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
        comment_post.setOnClickListener{
            if(comment_input.text?.isEmpty() == true){
                comment_input_layout.error = "comment cannot be empty"
            }else {

                val mapCommentAdd: HashMap<String, String> = HashMap()
                mapCommentAdd["content"] = comment_input.text.toString()
                mapCommentAdd["email"] = email.toString()
                mapCommentAdd["title"] = title.toString()
                println(mapCommentAdd)
                apiInterface.addComment(mapCommentAdd).enqueue((object: Callback<data> {
                    override fun onResponse(
                        call: Call<data>,
                        response: Response<data>
                    ) {
                        comment_input.getText()?.clear()
                        recylcerCommentsAdapter.updateData()
                        apiInterface.getComments(map).enqueue(object: Callback<ArrayList<data>>{
                            override fun onResponse(
                                call: Call<ArrayList<data>>,
                                response: Response<ArrayList<data>>
                            ) {
                                val comments = response.body()
                                if(comments!=null){
                                    println("first test\n"+comments)
                                    val list = mutableListOf<Comment>()

                                    for(i in comments){
                                        list.add(Comment(i.key.toString(),i.value.toString()))

                                    }

                                    recylcerCommentsAdapter = CommentAdapter(list)
                                    recylcerComments.adapter = recylcerCommentsAdapter
                                    recylcerComments.layoutManager = LinearLayoutManager(this@PostDetails, LinearLayoutManager.VERTICAL ,false)

                                }
                            }

                            override fun onFailure(call: Call<ArrayList<data>>, t: Throwable) {
                                println("khlet commnttr")
                            }


                        })
                    }

                    override fun onFailure(call: Call<data>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                }))
            }
        }



    }

}