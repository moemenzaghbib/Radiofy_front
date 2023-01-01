package esprit.tn.radiofy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import esprit.tn.radiofy.R
import esprit.tn.radiofy.models.User
import esprit.tn.radiofy.utils.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfile : AppCompatActivity() {
    lateinit var update_button: Button;
    lateinit var firstname_input: TextInputEditText
    lateinit var lastname_input: TextInputEditText
    lateinit var edit_last_name_layout: TextInputLayout
    lateinit var edit_first_name_layout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val apiInterface = ApiInterface.create()

        val email = intent.getStringExtra("email").toString()
        update_button = findViewById(R.id.update_button)
        firstname_input = findViewById(R.id.firstname_input)
        lastname_input = findViewById(R.id.lastname_input)
        edit_first_name_layout = findViewById(R.id.edit_first_name_layout)
        edit_last_name_layout = findViewById(R.id.edit_last_name_layout)

        update_button.setOnClickListener{
            val map: HashMap<String, String> = HashMap()
            map["email"] = email.toString()
            map["firstname"] = firstname_input.text.toString()
            map["lastname"] = lastname_input.text.toString()
            if(firstname_input.text!!.isEmpty()){
                edit_first_name_layout.error = getString(R.string.mustNotBeEmpty)
            }else if(lastname_input.text!!.isEmpty()){
                edit_last_name_layout.error = getString(R.string.mustNotBeEmpty)
            }else {
                apiInterface.editProfileUser(map)
                    .enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>)
                        {
                           var user = response.body();

                               println(user)
                               Toast.makeText(this@EditProfile, "Profile Updated!!", Toast.LENGTH_SHORT)
                                   .show()
                               val intent = Intent(this@EditProfile, MainScreenActivity::class.java)
                               startActivity(intent)
                               finish()

                             }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Toast.makeText(this@EditProfile, "An Error has occured, please try later!!", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this@EditProfile, MainScreenActivity::class.java)
                            startActivity(intent)
                            finish()
                                 }
                    })

    }}}
}