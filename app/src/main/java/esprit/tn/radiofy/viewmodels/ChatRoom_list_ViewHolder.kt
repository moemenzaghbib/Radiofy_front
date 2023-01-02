package esprit.tn.radiofy.viewmodels

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R
import esprit.tn.radiofy.activities.ChatRoomActivity
import esprit.tn.radiofy.activities.ChatRoomActivity_user
import esprit.tn.radiofy.activities.PostDetails

class ChatRoom_list_ViewHolder  (email: String,itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioName: TextView
        val radioEamil: TextView
        init {
            radioName = itemView.findViewById<TextView>(R.id.chat_room_name)
            radioEamil = itemView.findViewById<TextView>(R.id.chat_room_email)

            itemView.setOnClickListener {
                // navigate to other fragment with Safe Args
                if(email.toString().equals(radioEamil.text.toString())){
                    val intent = Intent(itemView.context, ChatRoomActivity::class.java )
                    intent.apply {
                        putExtra("email",email.toString() )
                        putExtra("radioName",radioName.text.toString() )
                        putExtra("radioEmail",radioEamil.text.toString() )

                    }
                    itemView.context.startActivity(intent)
                }else {
                    val intent = Intent(itemView.context, ChatRoomActivity_user::class.java )
                    intent.apply {
                        putExtra("email",email.toString() )
                        putExtra("radioName",radioName.text.toString() )
                        putExtra("radioEmail",radioEamil.text.toString() )

                    }
                    itemView.context.startActivity(intent)
                }

//                findNavController().navigate(action)



            }
        }

    }
