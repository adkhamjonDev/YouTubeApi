package com.example.youtubeapi.fragments

import android.content.Intent
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
import com.example.youtubeapi.R
import com.example.youtubeapi.VideoActivity
import com.example.youtubeapi.adapters.WatchLaterAdapter
import com.example.youtubeapi.databinding.FragmentWatchLaterBinding
import com.example.youtubeapi.models.RecentModel
import com.example.youtubeapi.models.WatchLaterModel
import com.example.youtubeapi.room.AppDataBase

class WatchLaterFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: FragmentWatchLaterBinding
    private lateinit var watchLaterAdapter: WatchLaterAdapter
    private lateinit var appDataBase: AppDataBase
    private  var obj:WatchLaterModel?=null
    private var pos:Int=0
    private lateinit var list: ArrayList<WatchLaterModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentWatchLaterBinding.inflate(inflater, container, false)
        appDataBase= AppDataBase.getInstance(requireContext())
        list= ArrayList(appDataBase.watchLaterDao().getAllWatchLater())
        if(list.size==0){
            binding.number.text="0 videos"
        }
        else{
            binding.number.text="${list.size} videos"
        }
        watchLaterAdapter= WatchLaterAdapter(list,object:WatchLaterAdapter.OnItemClickListener{
            override fun onClickMore(view: View, watchLaterModel: WatchLaterModel, position: Int) {
                obj=watchLaterModel
                pos=position
                val popup = PopupMenu(requireContext(),view)
                popup.setOnMenuItemClickListener(this@WatchLaterFragment)
                popup.inflate(R.menu.menu_popup6)
                popup.show()
            }

            override fun onClickItem(watchLaterModel: WatchLaterModel) {
                val intent = Intent(requireContext(), VideoActivity::class.java)
                intent.putExtra("videoTittle", watchLaterModel.videoName)
                intent.putExtra("videoDescription", watchLaterModel.videoDescription)
                intent.putExtra("videoDeId", watchLaterModel.videoId)
                startActivity(intent)
            }

        })
        binding.recView.adapter=watchLaterAdapter
        binding.logo.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.remove_from_watch_later->{
                appDataBase.watchLaterDao().removeWatchLater(obj!!)
                list.remove(obj)
                binding.number.text="${list.size} videos"
                watchLaterAdapter.notifyItemRemoved(pos)
                watchLaterAdapter.notifyItemRangeChanged(pos,list.size)
            }
            else->{
                return false
            }
        }
        return false
    }
}