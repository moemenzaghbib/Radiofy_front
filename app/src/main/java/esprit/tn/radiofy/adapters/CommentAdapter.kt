package esprit.tn.radiofy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R
import esprit.tn.radiofy.models.Comment
import esprit.tn.radiofy.viewmodels.CommentViewHolder


class CommentAdapter(val commentList: MutableList<Comment>): RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)

        return CommentViewHolder(view)
    }
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val autheur = commentList[position].autheur
        val content = commentList[position].content
        holder.autheur.text = autheur
        holder.content.text = content
    }
    fun updateData() {
        commentList.clear()

        notifyDataSetChanged()
    }
    override fun getItemCount() = commentList.size

}