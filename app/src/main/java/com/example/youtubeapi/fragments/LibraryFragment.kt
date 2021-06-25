package com.example.youtubeapi.fragments
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.youtubeapi.R
import com.example.youtubeapi.VideoActivity
import com.example.youtubeapi.adapters.PlaylistAdapter
import com.example.youtubeapi.adapters.RecentAdapter
import com.example.youtubeapi.databinding.CustomDialogBinding
import com.example.youtubeapi.databinding.FragmentLibraryBinding
import com.example.youtubeapi.databinding.PlaylistDialogBinding
import com.example.youtubeapi.models.PlaylistModel
import com.example.youtubeapi.models.RecentModel
import com.example.youtubeapi.models.WatchLaterModel
import com.example.youtubeapi.room.AppDataBase
import com.example.youtubeapi.viewModels.YoutubeViewModel
class LibraryFragment : Fragment(),PopupMenu.OnMenuItemClickListener  {
    private lateinit var recentAdapter: RecentAdapter
    private lateinit var playlistAdapter: PlaylistAdapter
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var list:ArrayList<RecentModel>
    private lateinit var listPlaylist:ArrayList<PlaylistModel>
    private lateinit var listLater:ArrayList<WatchLaterModel>
    private lateinit var appDataBase: AppDataBase
    private var obj:RecentModel?=null
    private var str:String?=null
    private var pos=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentLibraryBinding.inflate(inflater, container, false)
        appDataBase= AppDataBase.getInstance(requireContext())
        list= ArrayList(appDataBase.recentDao().getAllRecent())
        listPlaylist= ArrayList(appDataBase.playlistDao().getAllPlaylist())
        listLater= ArrayList(appDataBase.watchLaterDao().getAllWatchLater())
        if(listLater.size==0){
            binding.watchNumber.text="0 unwatched videos"
        }
        else{
            binding.watchNumber.text="${listLater.size} unwatched videos"
        }
        //-------------------------------------------------
        recentAdapter= RecentAdapter(list,object:RecentAdapter.OnItemClickListener{
            override fun onClickMore(view: View,recentModel: RecentModel,position:Int) {
                pos=position
                obj=recentModel
                str=recentModel.videoId
                val popup = PopupMenu(requireContext(),view)
                popup.setOnMenuItemClickListener(this@LibraryFragment)
                popup.inflate(R.menu.menu_popup4)
                popup.show()
            }

            override fun onClickItem(recentModel: RecentModel) {
                val intent = Intent(requireContext(), VideoActivity::class.java)
                intent.putExtra("videoTittle", recentModel.videoName)
                intent.putExtra("videoDescription", recentModel.videoDescription)
                intent.putExtra("videoDeId", recentModel.videoId)
                startActivity(intent)
            }
        })
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
        binding.recView.adapter=recentAdapter
        //-------------------------------------------------
        playlistAdapter= PlaylistAdapter(listPlaylist)
        binding.recView2.adapter=playlistAdapter
        //-------------------------------------------------
        binding.history.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }
        binding.watchLater.setOnClickListener {
            findNavController().navigate(R.id.watchLaterFragment)
        }
        binding.movies.setOnClickListener {
            findNavController().navigate(R.id.moviesShowFragment)
        }
        binding.myVideos.setOnClickListener {
            findNavController().navigate(R.id.yourVideosFragment)
        }
        //-------------------------------------------------
        binding.addPlaylist.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val binding1= PlaylistDialogBinding.inflate(inflater, null, false)
            builder.setView(binding1.root)
            builder.setTitle("Add Playlist")
            val alertDialog=builder.create()
            binding1.save.setOnClickListener {
                val name=binding1.name.text.toString()
                val description=binding1.description.text.toString()
                val playlistModel=PlaylistModel(name = name, description = description)
                listPlaylist.add(playlistModel)
                appDataBase.playlistDao().addPlaylist(playlistModel)
                playlistAdapter.notifyItemInserted(list.size)
                Toast.makeText(requireContext(), "Playlist Added", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()

            }
            alertDialog.show()
        }
        //-------------------------------------------------
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
                appDataBase.watchLaterDao().addWatchLater(
                    WatchLaterModel(videoName = obj?.videoName,
                        videoDescription = obj?.videoDescription,
                        videoId = obj?.videoId,
                        image = obj?.image)
                )
                listLater= ArrayList(appDataBase.watchLaterDao().getAllWatchLater())
                binding.watchNumber.text="${listLater.size} unwatched videos"
                Toast.makeText(requireContext(), "Saved to watch later", Toast.LENGTH_SHORT).show()
            }
            R.id.remove_from_watch_history->{
                appDataBase.recentDao().removeRecent(obj!!)
                list.remove(obj)
                recentAdapter.notifyItemRemoved(pos)
                recentAdapter.notifyItemRangeChanged(pos,list.size)
                Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show()
            }
            else->{
            return false
            }
        }
        return false
    }
}