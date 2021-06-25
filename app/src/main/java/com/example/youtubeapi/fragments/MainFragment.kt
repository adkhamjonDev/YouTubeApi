package com.example.youtubeapi.fragments
import android.app.AlertDialog
import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import com.example.youtubeapi.R
import com.example.youtubeapi.VideoActivity
import com.example.youtubeapi.adapters.VideoAdapter
import com.example.youtubeapi.databinding.CustomDialogBinding
import com.example.youtubeapi.databinding.FragmentMainBinding
import com.example.youtubeapi.models.Item
import com.example.youtubeapi.models.RecentModel
import com.example.youtubeapi.models.WatchLaterModel
import com.example.youtubeapi.network.ApiClient
import com.example.youtubeapi.room.AppDataBase
import com.example.youtubeapi.utils.Status
import com.example.youtubeapi.viewModels.ViewModelFactory
import com.example.youtubeapi.viewModels.YoutubeViewModel
class MainFragment : Fragment(),PopupMenu.OnMenuItemClickListener  {
    private lateinit var list:ArrayList<Item>
    private lateinit var binding: FragmentMainBinding
    private lateinit var videoAdapter: VideoAdapter
    private var str:String?=null
    private var obj:Item?=null
    private lateinit var youtubeViewModel: YoutubeViewModel
    private  val TAG = "MainFragment"
    private lateinit var appDataBase: AppDataBase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentMainBinding.inflate(inflater, container, false)
        appDataBase= AppDataBase.getInstance(requireContext())
        videoAdapter= VideoAdapter(object:VideoAdapter.OnItemClickListener{
            override fun onItemClick(item: Item) {
                val intent = Intent(requireContext(), VideoActivity::class.java)
                intent.putExtra("videoTittle", item.snippet.title)
                intent.putExtra("videoDescription", item.snippet.description)
                intent.putExtra("videoDeId", item.id.videoId)
                startActivity(intent)
                appDataBase.recentDao().addRecent(RecentModel(videoName = item.snippet.title,videoDescription = item.snippet.description,videoId = item.id.videoId,image = item.snippet.thumbnails.high.url))
//                val bundle= bundleOf("video" to item.id)
//                findNavController().navigate(R.id.videoFragment,bundle)
            }
            override fun onItemClickMore(item: Item, view: View) {
                obj=item
                str=item.id.videoId
                val popup = PopupMenu(requireContext(),view)
                popup.setOnMenuItemClickListener(this@MainFragment)
                popup.inflate(R.menu.menu_popup)
                popup.show()
            }
        })
        youtubeViewModel=ViewModelProviders.of(this,ViewModelFactory(ApiClient.apiService))[YoutubeViewModel::class.java]

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
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
                    appDataBase.watchLaterDao().addWatchLater(WatchLaterModel(videoName = obj?.snippet?.title,
                        videoDescription = obj?.snippet?.description,
                        videoId = obj?.id?.videoId,
                        image = obj?.snippet?.thumbnails?.high?.url))
                    Toast.makeText(requireContext(), "Saved to watch later", Toast.LENGTH_SHORT).show()
                }
                else->{
                return false
            }
            }
            return false
        }
}