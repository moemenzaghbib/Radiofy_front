package esprit.tn.radiofy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R
import esprit.tn.radiofy.models.User
import esprit.tn.radiofy.models.current_user
import esprit.tn.radiofy.utils.ApiInterface
import esprit.tn.radiofy.viewmodels.current_usersViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrentUsersAdapter(email_radio: String, val current_user_list: MutableList<current_user>): RecyclerView.Adapter<current_usersViewHolder>() {
    var email_radio1:String = email_radio;
    val apiInterface = ApiInterface.create()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): current_usersViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.currentuser_item, parent, false)

            return current_usersViewHolder(view)
        }
        override fun onBindViewHolder(holder: current_usersViewHolder, position: Int) {
            val email = current_user_list[position].email
            val name = current_user_list[position].name
            holder.email.text = email
            holder.name.text = name
            holder.btn_banne.setOnClickListener {

                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Delete Education")
                    .setMessage("are you sure you want to banne this user")
                    .setPositiveButton("Yes"){ dialogInterface, which ->
//                        AppDataBase.getDatabase(holder.itemView.context).educationDao().delete(edc)
//                      apiInterface  educationList.removeAt(position)

                        val map_user_to_ban: HashMap<String, String> = HashMap()
                        map_user_to_ban["email"] = name
                        map_user_to_ban["radio"] = email_radio1
                        apiInterface.BanneUser(map_user_to_ban).enqueue(object:
                            Callback<User> {
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                println("khedmet add connected user")

                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {
                                println("mekhdmtch connected ")
                            }


                        })

                    }.setNegativeButton("No"){dialogInterface, which ->
                        dialogInterface.dismiss()
                    }.create().show()

            }
        }
        fun updateData() {
            current_user_list.clear()

            notifyDataSetChanged()
        }
        override fun getItemCount() = current_user_list.size

    }