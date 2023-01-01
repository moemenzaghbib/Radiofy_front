package esprit.tn.radiofy.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.activities.PostDetails
import esprit.tn.radiofy.databinding.EventItemBinding
import esprit.tn.radiofy.models.EventItem
import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.activity_main.view.*
//import kotlinx.android.synthetic.main.event_item.view.*

class EventAdapter(private val context: Context,val email_user: String): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

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
        Log.e("nbre",items.size.toString())
        return items.size
    }
    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int)  =holder.bind(items[position])

    inner class ViewHolder(val binding: EventItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: EventItem) = with(itemView) {
            //use two way binding
            //BR - is auto-generating class
            binding.setVariable(esprit.tn.radiofy.BR.item, item)
            //set Image
            Picasso.get().load(item.image).into(binding.imageView)

            itemView.setOnClickListener {
                // navigate to other fragment with Safe Args
                println("teeeeeeest"+email_user)
               val intent = Intent(itemView.context,PostDetails::class.java )
                intent.apply {
                    putExtra("title",item.title.toString() )
                    putExtra("email",email_user.toString())
                }
                itemView.context.startActivity(intent)
//                findNavController().navigate(action)



            }
        }
    }
}