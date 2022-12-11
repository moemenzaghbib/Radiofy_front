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
import android.util.Patterns
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Switch
import com.example.myapplication.ForgotPasswordActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

const val EMAIL = "email"
class SignInActivity : AppCompatActivity() {

    lateinit var txtEmail: TextInputEditText
    lateinit var layoutEmail: TextInputLayout
    lateinit var txtpassword: TextInputEditText
    lateinit var layoutPassword: TextInputLayout
    lateinit var registernow: TextView
    lateinit var btnSignIn: Button
    lateinit var mSharedPref: SharedPreferences
    lateinit var checkRememberMe: CheckBox
  //  lateinit var checkRememberMe: Switch

    lateinit var buttonForgotPassword : TextView
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    lateinit var gotosignup: TextView






    val apiInterface = ApiInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // new sign in
        setContentView(R.layout.activity_sign2)

        gotosignup = findViewById<Button>(R.id.gotosignup)



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("360897051240-7s15c6r9evpj5ot5vhe8039j7mhqd6bb.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val googleLoginButton = findViewById<Button>(R.id.sgningooglebtn)
        googleLoginButton.setOnClickListener {
            Log.e("lenna ? ","test");
            signIn()
        }



        mSharedPref = getSharedPreferences("PREF_NAME", MODE_PRIVATE);

        txtEmail = findViewById(R.id.email)
        txtpassword = findViewById(R.id.password)
        layoutEmail = findViewById(R.id.emailContainer)
        layoutPassword = findViewById(R.id.passwordContainer)
        btnSignIn = findViewById(R.id.sgninbtn)
       // registernow = findViewById(R.id.newUsertextbutton)
        checkRememberMe = findViewById(R.id.checkBox)
        buttonForgotPassword = findViewById(R.id.ForgotPassword_Button)


        // go to sign up
        gotosignup.setOnClickListener {
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(intent)
        }


        if (mSharedPref.getBoolean("IS_REMEMBRED", false) ){
            Log.e("hhhhhhhhhh",mSharedPref.getBoolean("IS_REMEMBRED", false).toString())

            startActivity(Intent(this , MainScreenActivity::class.java))

        }
        btnSignIn.setOnClickListener {



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
                                putString("email", map["email"])
                                putString("password", txtpassword.text.toString())
                            }.apply()
                        } else {
                            mSharedPref.edit().clear().apply()

                        }


                            Toast.makeText(this@SignInActivity, "Connection succeeded!", Toast.LENGTH_SHORT)
                                .show()
                            println(user)
                            Log.e("email chytbth",user?.email.toString())
                            val intent = Intent(this@SignInActivity, MainScreenActivity::class.java)

                                .putExtra("email", map["email"])
                                .putExtra("firstname", user?.firstname.toString())
                                .putExtra("lastname", user?.lastname.toString())


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
        gotosignup.setOnClickListener {
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            }
        buttonForgotPassword.setOnClickListener {
            val intent = Intent(this@SignInActivity, ForgotPasswordActivity::class.java)

            startActivity(intent)
            finish()

        }

    }
    private fun validate(): Boolean {

        layoutEmail.error = null
        layoutPassword.error = null

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

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )
    }

// ...

    companion object {


        const val RC_SIGN_IN = 9001

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Log.e("hamma","tahan")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {

            val account = completedTask.getResult(
                ApiException::class.java

            )
            Log.e("hamma","tahan")
            // Signed in successfully
            println(account)

            val map: HashMap<String, String> = HashMap()
            map["googleID"] =  account.id.toString()
            Log.e("google:id",account.id.toString())
            Log.e("google:id",account.givenName.toString())
            Log.e("google:id",account.familyName.toString())
            Log.e("google:id",account.email.toString())

            apiInterface.googleVerifier(map)
                .enqueue(object : Callback<User> {

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        val user = response.body()
                        if (user != null) {
                            Log.e("lkah","aasba")
                            apiInterface.googleSignIn(map)
                                .enqueue(object : Callback<User> {
                                    override fun onResponse(
                                        call: Call<User>,
                                        response: Response<User>
                                    ) {
                                      println(user)
                                        val intent = Intent(this@SignInActivity, MainScreenActivity::class.java)
                                            .putExtra("email", user?.email.toString())
                                            .putExtra("firstname", user?.firstname.toString())
                                            .putExtra("lastname", user?.lastname.toString())
                                        startActivity(intent)
                                        finish()
                                    }

                                    override fun onFailure(call: Call<User>, t: Throwable) {
                                        TODO("Not yet implemented")
                                        Log.e("faama mochkla","aasbtin")
                                    }

                                })

                        }
                        else {
//                            Log.e("melkitouch aasba","aasba")
                            val map1: HashMap<String, String> = HashMap()
                            map1["googleID"] =  account.id.toString()
                            map1["firstname"] =  account.givenName.toString()
                            map1["lastname"] =  account.familyName.toString()
                            map1["email"] =  account.email.toString()
                            apiInterface.googleSignUp(map1)
                                .enqueue(object : Callback<User> {
                                    override fun onResponse(
                                        call: Call<User>,
                                        response: Response<User>
                                    ) {
                                        Log.e("amlneh el compte","aasbtin")
                                    }

                                    override fun onFailure(call: Call<User>, t: Throwable) {
                                        TODO("Not yet implemented")
                                        Log.e("faama mochkla","aasbtin")
                                    }

                                })
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("failed verifier","failed to connect to google verifier")
                    }


                })
//            val googleId = account?.id ?: ""}
//            Log.i("Google ID",googleId)
//            val googleFirstName = account?.givenName ?: ""
//            Log.i("Google First Name", googleFirstName)
//            val googleLastName = account?.familyName ?: ""
//            Log.i("Google Last Name", googleLastName)
//            val googleEmail = account?.email ?: ""
//            Log.i("Google Email", googleEmail)
//            val googleProfilePicURL = account?.photoUrl.toString()
//            Log.i("Google Profile Pic URL", googleProfilePicURL)
//            val googleIdToken = account?.idToken ?: ""
//            Log.i("Google ID Token", googleIdToken)
        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }


}