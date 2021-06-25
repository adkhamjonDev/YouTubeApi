package com.example.youtubeapi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.youtubeapi.R
import com.example.youtubeapi.adapters.RvAdapter
import com.example.youtubeapi.databinding.FragmentSearchBinding
import com.example.youtubeapi.models.Item
import com.example.youtubeapi.network.ApiClient
import com.example.youtubeapi.utils.Status
import com.example.youtubeapi.viewModels.ViewModelFactory
import com.example.youtubeapi.viewModels.YoutubeViewModel

class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var list:ArrayList<Item>
    private lateinit var youtubeViewModel: YoutubeViewModel
    private  val TAG = "SearchFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSearchBinding.inflate(inflater, container, false)
        list=ArrayList()
        youtubeViewModel= ViewModelProviders.of(this, ViewModelFactory(ApiClient.apiService))[YoutubeViewModel::class.java]
        youtubeViewModel.getYoutubeLiveData().observe(viewLifecycleOwner,{
            when(it.status){
                Status.ERROR->{
                    binding.progress.visibility=View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING->{
                    binding.progress.visibility=View.VISIBLE
                }
                Status.SUCCESS->{
                    binding.progress.visibility=View.INVISIBLE
                    list.addAll(it.data!!.items)
                    Log.d(TAG, "onCreateView: ${it.data.items}")
                }
            }
        })
        rvAdapter= RvAdapter(list,object :RvAdapter.OnItemClickListener{
            override fun onItemClick(item: Item) {

            }
            override fun onItemClickMore(item: Item, view: View) {

            }
        })
        binding.recView.adapter=rvAdapter
        binding.searchView.setOnCloseListener {
            findNavController().popBackStack()
            false
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                rvAdapter.filter.filter(newText)
                return false
            }
        })
        binding.logo.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}