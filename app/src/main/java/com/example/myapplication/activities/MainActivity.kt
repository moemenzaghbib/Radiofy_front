package com.example.myapplication.activities
import android.content.Intent
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.models.User
import com.example.myapplication.utils.ApiInterface
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class MainActivity : AppCompatActivity() {
    lateinit var txtFirstName: TextInputEditText
    lateinit var layoutFirstName: TextInputLayout
    lateinit var txtlastName: TextInputEditText
    lateinit var layoutLastName: TextInputLayout
    lateinit var txtEmail: TextInputEditText
    lateinit var layoutEmail: TextInputLayout
    lateinit var txtpassword: TextInputEditText
    lateinit var layoutPassword: TextInputLayout
    lateinit var gotosignin: TextView

    lateinit var btnSignup: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // class sign up
        setContentView(R.layout.activity_signup)

        txtFirstName = findViewById(R.id.firstname)
        txtlastName = findViewById(R.id.lastname)
        txtEmail = findViewById(R.id.email)
        txtpassword = findViewById(R.id.password)
        btnSignup = findViewById(R.id.signupbtn)
        layoutFirstName = findViewById(R.id.firstnameContainer)
        layoutLastName = findViewById(R.id.lastnameContainer)
        layoutEmail = findViewById(R.id.emailContainer)
        layoutPassword = findViewById(R.id.passwordContainer)
        gotosignin = findViewById<Button>(R.id.gotosignin)



        // go to sign in
        gotosignin.setOnClickListener {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)

        }

        // btn sign up
        btnSignup.setOnClickListener{
            val apiInterface = ApiInterface.create()

            if (validate()){
                val map: HashMap<String, String> = HashMap()
                map["firstname"] = txtFirstName.text.toString()
                map["lastname"] = txtlastName.text.toString()
                map["email"] = txtEmail.text.toString()
                map["password"] = txtpassword.text.toString()

                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )

                apiInterface.register(map)
                    .enqueue(object : Callback<User> {
                        override fun onResponse( call: Call<User>, response: Response<User> )
                        {
                            val user = response.body()
                            println(user)
                            if (user != null) {
                                Log.e("//////////////////user ", response.body().toString())
                                Log.e("//////////////////user ", response.body().toString())

                            }
                            val intent = Intent(this@MainActivity, SignInActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "Connexion error!", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } )

            }



        }
    }

    private fun doSignup() {

    }
    private fun validate(): Boolean {
        layoutFirstName.error = null
        layoutLastName.error = null
        layoutEmail.error = null
        layoutPassword.error = null

        if (txtFirstName.text!!.isEmpty()){
            layoutFirstName.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtlastName.text!!.isEmpty()){
            layoutLastName.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.text.toString()).matches()){
            layoutEmail.error = getString(R.string.checkYourEmail)
            return false
        }
        if (txtpassword.text!!.isEmpty()){
            layoutPassword.error = getString(R.string.mustNotBeEmpty)
            return false
        }



        return true
    }



}