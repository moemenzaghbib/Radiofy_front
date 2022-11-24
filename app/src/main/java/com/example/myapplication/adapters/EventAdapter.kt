package com.example.myapplication.adapters

import android.content.Context
import android.content.SyncAdapterType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.activities.MainScreenActivity
import com.example.myapplication.databinding.EventItemBinding
import com.example.myapplication.fragments.HomeDirections
import com.example.myapplication.models.EventItem
import com.squareup.picasso.Picasso
import java.text.FieldPosition

class EventAdapter(private val context: Context): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private var items = mutableListOf<EventItem>()
    fun setListData(data: MutableList<EventItem>) {
        items = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int)  =holder.bind(items[position])

    inner class ViewHolder(val binding: EventItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: EventItem) = with(itemView) {
            //use two way binding
            //BR - is auto-generating class
            binding.setVariable(BR.item, item)
            //set Image
            Picasso.get().load(item.image).into(binding.imageView)

            itemView.setOnClickListener {
                // navigate to other fragment with Safe Args
                val action = HomeDirections.actionHome2ToEventDetailFragment(item)
                    //HomeDirections.actionHome2ToEventDetailFragment(item)
                //findNavController().navigate(action)
                findNavController().navigate(action)



            }
        }
    }
}