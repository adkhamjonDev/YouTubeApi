package com.example.youtubeapi.fragments
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.youtubeapi.R
import com.example.youtubeapi.VideoActivity
import com.example.youtubeapi.adapters.SubscribeTopItemAdapter
import com.example.youtubeapi.adapters.VideoAdapter
import com.example.youtubeapi.adapters.VideoAdapter2
import com.example.youtubeapi.databinding.CustomDialogBinding
import com.example.youtubeapi.databinding.FragmentSubscribeBinding
import com.example.youtubeapi.models.Item
import com.example.youtubeapi.models.RecentModel
import com.example.youtubeapi.models.SubscribeItemModel
import com.example.youtubeapi.models.WatchLaterModel
import com.example.youtubeapi.network.ApiClient
import com.example.youtubeapi.room.AppDataBase
import com.example.youtubeapi.utils.Status
import com.example.youtubeapi.viewModels.ViewModelFactory
import com.example.youtubeapi.viewModels.YoutubeViewModel
class SubscribeFragment : Fragment(),PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: FragmentSubscribeBinding
    private lateinit var list:ArrayList<SubscribeItemModel>
    private lateinit var subscribeTopItemAdapter: SubscribeTopItemAdapter
    private lateinit var videoAdapter: VideoAdapter2
    private var obj:Item?=null
    private lateinit var youtubeViewModel: YoutubeViewModel
    private lateinit var appDataBase: AppDataBase
    private var str:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSubscribeBinding.inflate(inflater, container, false)
        loadData()
        appDataBase= AppDataBase.getInstance(requireContext())
        subscribeTopItemAdapter= SubscribeTopItemAdapter(list)
        binding.recViewTop.adapter=subscribeTopItemAdapter
        youtubeViewModel= ViewModelProviders.of(this, ViewModelFactory(ApiClient.apiService))[YoutubeViewModel::class.java]
        videoAdapter= VideoAdapter2(object:VideoAdapter2.OnItemClickListener{
            override fun onItemClick(item: Item) {
                appDataBase.recentDao().addRecent(
                    RecentModel(
                        videoName = item.snippet.title,
                        videoId = item.id.videoId,
                        image = item.snippet.thumbnails.high.url)
                )
                val intent = Intent(requireContext(), VideoActivity::class.java)
                intent.putExtra("videoTittle", item.snippet.title)
                intent.putExtra("videoDescription", item.snippet.description)
                intent.putExtra("videoDeId", item.id.videoId)
                startActivity(intent)

            }
            override fun onItemClickMore(item: Item, view: View) {
                obj=item
                str=item.id.videoId
                val popup = PopupMenu(requireContext(),view)
                popup.setOnMenuItemClickListener(this@SubscribeFragment)
                popup.inflate(R.menu.menu_popup)
                popup.show()
            }
            override fun onItemClickReminder() {
                Toast.makeText(requireContext(), "Reminder set !", Toast.LENGTH_SHORT).show()
            }
        })
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
        binding.all.setOnClickListener {
            findNavController().navigate(R.id.channelListFragment)
        }
        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        binding.videos.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val binding1 = CustomDialogBinding.inflate(inflater, null, false)
            builder.setView(binding1.root)
            val alertDialog = builder.create()
            alertDialog.show()
        }
        return binding.root
    }
    private fun loadData(){
        list= ArrayList()
        for (i in 0 until 15){
            list.add(SubscribeItemModel("PDP Academy",R.drawable.logo2))
        }
    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.share->{
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "https://www.youtube.com/watch?v=${str}"
                )
                sendIntent.type = "text/plain"
                startActivity(sendIntent)

            }
            R.id.save_later->{
                appDataBase.watchLaterDao().addWatchLater(
                    WatchLaterModel(videoName = obj?.snippet?.title,
                    videoDescription = obj?.snippet?.description,
                    videoId = obj?.id?.videoId,
                    image = obj?.snippet?.thumbnails?.high?.url)
                )
                Toast.makeText(requireContext(), "Saved to watch later", Toast.LENGTH_SHORT).show()
            }
            else->{
            return false
        }
        }
        return false
    }
}