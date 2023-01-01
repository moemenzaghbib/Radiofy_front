package esprit.tn.radiofy.viewmodels

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R


    class current_usersViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val email: TextView
        val name: TextView
        val btn_banne : Button

        init {
            email = itemView.findViewById<TextView>(R.id.userEmail_currentUser)
            name = itemView.findViewById<TextView>(R.id.userName_currentUser)
            btn_banne = itemView.findViewById<Button>(R.id.banne_user_button)
        }
    }