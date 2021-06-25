package com.example.youtubeapi.fragments
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.youtubeapi.MainActivity
import com.example.youtubeapi.R
import com.example.youtubeapi.adapters.VideoAdapter
import com.example.youtubeapi.databinding.FragmentVideoBinding
import com.example.youtubeapi.models.Id
import com.example.youtubeapi.models.Item
import com.example.youtubeapi.network.ApiClient
import com.example.youtubeapi.utils.Status
import com.example.youtubeapi.viewModels.ViewModelFactory
import com.example.youtubeapi.viewModels.YoutubeViewModel

class VideoFragment : Fragment(),PopupMenu.OnMenuItemClickListener {
    private  var obj: Id?=null
    private lateinit var binding: FragmentVideoBinding
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var youtubeViewModel: YoutubeViewModel
    private  val TAG = "VideoFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentVideoBinding.inflate(inflater, container, false)
        (activity as MainActivity?)?.hideBottomNavBar()
        if(arguments!=null){
            obj=arguments?.getSerializable("video")as Id
        }
        videoAdapter= VideoAdapter(object:VideoAdapter.OnItemClickListener{
            override fun onItemClick(item: Item) {

            }
            override fun onItemClickMore(item: Item, view: View) {
                val popup = PopupMenu(requireContext(),view)
                popup.setOnMenuItemClickListener(this@VideoFragment)
                popup.inflate(R.menu.menu_popup)
                popup.show()
            }
        })
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
                    videoAdapter.submitList(it.data?.items)
                }
            }
        })
        binding.recView.adapter=videoAdapter
        binding.cancel.setOnClickListener {
            binding.linear5.visibility=View.GONE
            binding.mainLinear.visibility=View.GONE

            binding.linear.visibility=View.VISIBLE
            binding.linear2.visibility=View.VISIBLE
            binding.linear3.visibility=View.VISIBLE
            binding.linear4.visibility=View.VISIBLE
            binding.recView.visibility=View.VISIBLE
        }
        binding.comment.setOnClickListener {
            binding.linear5.visibility=View.VISIBLE
            binding.mainLinear.visibility=View.VISIBLE

            binding.linear.visibility=View.GONE
            binding.linear2.visibility=View.GONE
            binding.linear3.visibility=View.GONE
            binding.linear4.visibility=View.GONE
            binding.recView.visibility=View.GONE
        }
        return binding.root
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

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity?)?.showBottomNavBar()
    }
}