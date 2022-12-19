package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Comment
import com.example.myapplication.models.current_user
import com.example.myapplication.viewmodels.CommentViewHolder
import com.example.myapplication.viewmodels.current_usersViewHolder


class CurrentUsersAdapter(val current_user_list: MutableList<current_user>): RecyclerView.Adapter<current_usersViewHolder>() {
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
//                        educationList.removeAt(position)
                        notifyDataSetChanged()

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