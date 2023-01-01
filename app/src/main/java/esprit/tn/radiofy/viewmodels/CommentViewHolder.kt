package esprit.tn.radiofy.viewmodels

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R

class CommentViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val autheur: TextView
    val content: TextView
    init {
        autheur = itemView.findViewById<TextView>(R.id.autheur_comment)
        content = itemView.findViewById<TextView>(R.id.content_comment)
    }
}