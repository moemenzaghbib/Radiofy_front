package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEventDetailBinding
import com.example.myapplication.viewmodels.EventViewModel
import com.squareup.picasso.Picasso

class EventDetailFragment : Fragment() {

        private lateinit var binding: FragmentEventDetailBinding
    private val  viewModel by lazy {  ViewModelProvider(this).get(EventViewModel::class.java) }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//
//
//        binding = FragmentEventDetailBinding.inflate(inflater, container, false)
//        binding.progressLayout.visibility = View.VISIBLE
//       // val args  = EventDetailFragmentArgs.fromBundle((requireArguments()))
//        //val item = args.eventItem
//
//
////        viewModel.fetchEvent(item!!.url).observe(viewLifecycleOwner, Observer {
////            binding.TitleTextView.text.insert(0,item!!.title)
////            binding.DateTextView.text.insert(0,item!!.date)
////            binding.detailTextView.text.insert(0,item!!.desc)
////            Picasso.get().load(item!!.image).into(binding.imageView)
////            binding.progressLayout.visibility = View.GONE
//
//
//        })
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_event_detail, container, false)
//    }

}