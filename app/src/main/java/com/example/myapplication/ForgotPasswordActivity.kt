package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.models.User
import com.example.myapplication.models.data
import com.example.myapplication.utils.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var restor_button : Button
    lateinit var email_input : TextInputEditText
    lateinit var email_input_layout : TextInputLayout
    val apiInterface = ApiInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        restor_button = findViewById(R.id.resetbtn)
        email_input = findViewById(R.id.email)
        email_input_layout = findViewById(R.id.emailContainer)

        restor_button.setOnClickListener {
            if (email_input.text!!.isEmpty()){
                email_input_layout.error = getString(R.string.mustNotBeEmpty)
               } else {
                val map: HashMap<String, String> = HashMap()
                map["email"] = email_input.text.toString()

                apiInterface.forgotPassword(map)
                    .enqueue(object : Callback<data> {

                        override fun onResponse(call: Call<data>, response: Response<data>) {
                            println("lehne")
                            println(response.body()?.value)
                            if (response.body()!= null)
                            {
                                Toast.makeText(this@ForgotPasswordActivity, "Please check your email for the confirmation code!", Toast.LENGTH_SHORT)
                                    .show()
                            }else {
                                Toast.makeText(this@ForgotPasswordActivity, "No account with this email exists!", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }

                        override fun onFailure(call: Call<data>, t: Throwable) {
                            Toast.makeText(this@ForgotPasswordActivity, "Connection Error!", Toast.LENGTH_SHORT)
                                .show()
                        }

                    })


            }

        }
    }
}