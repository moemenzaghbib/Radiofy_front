package esprit.tn.radiofy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import esprit.tn.radiofy.R
import esprit.tn.radiofy.databinding.FragmentEventDetailBinding
import esprit.tn.radiofy.viewmodels.EventViewModel

class EventDetailFragment : Fragment() {

        private lateinit var binding: FragmentEventDetailBinding
    private val  viewModel by lazy {  ViewModelProvider(this).get(EventViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        binding.progressLayout.visibility = View.VISIBLE
//        val args  = EventDetailFragmentArgs.fromBundle((requireArguments()))
//        val item = args.eventItem


//        viewModel.fetchEvent(item!!.url).observe(viewLifecycleOwner, Observer {
//            binding.TitleTextView.text.insert(0,item!!.title)
//            binding.DateTextView.text.insert(0,item!!.date)
//            binding.detailTextView.text.insert(0,item!!.desc)
//            Picasso.get().load(item!!.image).into(binding.imageView)
//            binding.progressLayout.visibility = View.GONE


//        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

}