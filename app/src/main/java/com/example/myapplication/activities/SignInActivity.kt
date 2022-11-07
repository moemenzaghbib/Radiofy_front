package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.models.User
import com.example.myapplication.utils.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.SharedPreferences
import android.widget.Switch


class SignInActivity : AppCompatActivity() {

    lateinit var txtEmail: TextInputEditText
    lateinit var layoutEmail: TextInputLayout
    lateinit var txtpassword: TextInputEditText
    lateinit var layoutPassword: TextInputLayout
    lateinit var registernow: TextView
    lateinit var btnSignIn: Button
    lateinit var mSharedPref: SharedPreferences
    lateinit var checkRememberMe: Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mSharedPref = getSharedPreferences("PREF_NAME", MODE_PRIVATE);

        txtEmail = findViewById(R.id.Email_Login_Input)
        txtpassword = findViewById(R.id.Password_Login_Input)
        layoutEmail = findViewById(R.id.Email_Login_Layout)
        layoutPassword = findViewById(R.id.Password_Login_Layout)
        btnSignIn = findViewById(R.id.Login_Button)
        registernow = findViewById(R.id.newUsertextbutton)
        checkRememberMe = findViewById(R.id.Switch_rememberme)

        if (mSharedPref.getBoolean("IS_REMEMBRED", false) ){
            Log.e("hhhhhhhhhh",mSharedPref.getBoolean("IS_REMEMBRED", false).toString())

            startActivity(Intent(this , MainScreenActivity::class.java))

        }
        btnSignIn.setOnClickListener {

            val apiInterface = ApiInterface.create()

            if (validate()){
                val map: HashMap<String, String> = HashMap()
                map["email"] = txtEmail.text.toString()
                map["password"] = txtpassword.text.toString()
                 window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)



                apiInterface.signin(map)
                    .enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>)
                        {    val user = response.body()
                            if (checkRememberMe.isChecked) {
                            mSharedPref.edit().apply {
                                putBoolean("IS_REMEMBRED", true)
                                putString("email", txtEmail.text.toString())
                                putString("password", txtpassword.text.toString())
                            }.apply()
                        } else {
                            mSharedPref.edit().clear().apply()

                        }


                            Toast.makeText(this@SignInActivity, "Connection succeeded!", Toast.LENGTH_SHORT)
                                .show()
                            println(user)
                            val intent = Intent(this@SignInActivity, MainScreenActivity::class.java)
                            startActivity(intent)
                            finish()
                            if (user != null) {

                                Log.e("//////////////////user ", response.body().toString())

                            }
                            else {
                                Toast.makeText(this@SignInActivity, "Email or Password wrong, Please check your informations!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Toast.makeText(this@SignInActivity, "Connexion error!", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } )

            }



        }
        registernow.setOnClickListener {
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            }

    }
    private fun validate(): Boolean {

        layoutEmail.error = null
        layoutPassword.error = null

        if (txtEmail.text!!.isEmpty()){
            layoutEmail.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtpassword.text!!.isEmpty()){
            layoutPassword.error = getString(R.string.mustNotBeEmpty)
            return false
        }
      return true
    }
}