package esprit.tn.radiofy.viewmodels

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R
import esprit.tn.radiofy.activities.ChatRoomActivity
import esprit.tn.radiofy.activities.PostDetails

class ChatRoom_list_ViewHolder  (email: String,itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioName: TextView

        init {
            radioName = itemView.findViewById<TextView>(R.id.chat_room_name)

            itemView.setOnClickListener {
                // navigate to other fragment with Safe Args

                val intent = Intent(itemView.context, ChatRoomActivity::class.java )
                intent.apply {
                    putExtra("email",email.toString() )
                    putExtra("radioName",radioName.text.toString() )

                }
                itemView.context.startActivity(intent)
//                findNavController().navigate(action)



            }
        }

    }
