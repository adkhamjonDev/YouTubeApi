package com.example.youtubeapi.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.youtubeapi.MainActivity
import com.example.youtubeapi.R
import com.example.youtubeapi.adapters.ChannelListAdapter
import com.example.youtubeapi.adapters.SubscribeTopItemAdapter
import com.example.youtubeapi.databinding.FragmentChannelListBinding
import com.example.youtubeapi.models.SubscribeItemModel

class ChannelListFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: FragmentChannelListBinding
    private lateinit var list:ArrayList<SubscribeItemModel>
    private lateinit var channelListAdapter: ChannelListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentChannelListBinding.inflate(inflater, container, false)
        loadData()
        channelListAdapter= ChannelListAdapter(list)
        binding.recView.adapter=channelListAdapter

        binding.logo.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.most.setOnClickListener {
            val popup = PopupMenu(requireContext(),it)
            popup.setOnMenuItemClickListener(this@ChannelListFragment)
            popup.inflate(R.menu.menu_popup2)
            popup.show()
        }
        binding.icon.setOnClickListener {
            val popup = PopupMenu(requireContext(),it)
            popup.setOnMenuItemClickListener(this@ChannelListFragment)
            popup.inflate(R.menu.menu_popup3)
            popup.show()
        }
        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        return binding.root
    }
    private fun loadData(){
        list= ArrayList()
        for (i in 0 until 25){
            list.add(SubscribeItemModel("PDP Academy",R.drawable.logo2))
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.save_later->{

            }else->{
            return false
        }
        }
        return false
    }
}