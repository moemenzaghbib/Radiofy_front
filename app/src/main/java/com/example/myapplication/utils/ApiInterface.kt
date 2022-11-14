package com.example.myapplication.utils
import android.provider.ContactsContract
import com.example.myapplication.models.User
import com.example.myapplication.models.data
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiInterface {

    // @POST("login")
    //  fun seConnecter(@Query("log") login: String, @Query("pwd") password: String): Call<User>
    // @POST("addpost")
    //fun AddPost(@Query("id") id: Int, @Query("message") message: String): Call<User>

    // @POST("updatePwd")
    //  fun ChangePWD(@Query("pwd") password: String, @Query("log") login : String): Call<User>
    @POST("signin")
    fun signin(@Body map: HashMap<String ,String>):Call<User>

    @POST("signup")
    fun register(@Body map: HashMap<String ,String>):Call<User>

    @POST("googleVerifier")
    fun googleVerifier(@Body map: HashMap<String ,String>):Call<User>

    @POST("googleSignIn")
    fun googleSignIn(@Body map: HashMap<String ,String>):Call<User>

    @POST("googleSignup")
    fun googleSignUp(@Body map: HashMap<String ,String>):Call<User>

    @POST("forgot")
    fun forgotPassword(@Body map: HashMap<String, String>):Call<data>







    companion object {

    fun create() : ApiInterface {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:9090/user/")
            .build()

        return retrofit.create(ApiInterface::class.java)
    }



  /*  @POST("signin")
    fun InsertU(
        @Query("firstname") login: String,
        @Query("lastname") password: String,
        @Query("email") name: String,
        @Query("password") type: String
    )
            : Call<User>


    companion object {

        var BASE_URL = "http://10.0.2.2:9091/"


        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }


    }*/
}}