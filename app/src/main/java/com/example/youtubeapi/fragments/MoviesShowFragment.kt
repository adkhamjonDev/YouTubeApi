package com.example.youtubeapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.youtubeapi.R
import com.example.youtubeapi.databinding.FragmentMoviesShowBinding

class MoviesShowFragment : Fragment() {

    private lateinit var binding:FragmentMoviesShowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentMoviesShowBinding.inflate(inflater, container, false)





        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        binding.logo.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}