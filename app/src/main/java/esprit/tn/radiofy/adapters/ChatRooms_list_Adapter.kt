package esprit.tn.radiofy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R
import esprit.tn.radiofy.models.ChatRoom
import esprit.tn.radiofy.models.current_user
import esprit.tn.radiofy.viewmodels.ChatRoom_list_ViewHolder
import esprit.tn.radiofy.viewmodels.current_usersViewHolder

class ChatRooms_list_Adapter (email: String,val chatrooms_list: MutableList<ChatRoom>): RecyclerView.Adapter<ChatRoom_list_ViewHolder>() {
    val em = email;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoom_list_ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chatroom_item, parent, false)


        return ChatRoom_list_ViewHolder(em,view)
    }
    override fun onBindViewHolder(holder: ChatRoom_list_ViewHolder, position: Int) {
        val radioName = chatrooms_list[position].radioName
        val radioEmail = chatrooms_list[position].radioEmail

       holder.radioName.text =radioName
        holder.radioEamil.text =radioEmail


    }
    fun updateData() {
        chatrooms_list.clear()

        notifyDataSetChanged()
    }
    override fun getItemCount() = chatrooms_list.size


}