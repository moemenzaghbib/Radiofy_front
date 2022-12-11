package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.activities.EMAIL
import com.example.myapplication.adapters.EventAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.viewmodels.EventViewModel


class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val  viewModel by lazy {  ViewModelProvider(this).get(EventViewModel::class.java) }
    private lateinit var adapter: EventAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView( inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.ProgressBar.visibility + View.VISIBLE
        binding.RecycleView.layoutManager = LinearLayoutManager(requireActivity())
        var email1 = ""
        arguments?.let {
            email1 = requireArguments().getString(EMAIL,"email")
            println("moemen karez w bde ytesti lehen\n"+email1)

        }

        adapter = EventAdapter((requireActivity()),email1)
        binding.RecycleView.adapter = adapter
//

        //important: in fragment need set owner
        viewModel.fetchData().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)

            binding.ProgressBar.visibility = View.GONE
        })

        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance(email_sent: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(EMAIL, email_sent)


                }
            }
    }

}