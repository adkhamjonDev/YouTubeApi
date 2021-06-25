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
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.youtubeapi.R
import com.example.youtubeapi.VideoActivity
import com.example.youtubeapi.adapters.HistoryAdapter
import com.example.youtubeapi.databinding.FragmentHistoryBinding
import com.example.youtubeapi.databinding.LibraryItemBinding
import com.example.youtubeapi.models.RecentModel
import com.example.youtubeapi.room.AppDataBase
class HistoryFragment : Fragment(),PopupMenu.OnMenuItemClickListener  {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var list: ArrayList<RecentModel>
    private lateinit var appDataBase: AppDataBase
    private  var obj:RecentModel?=null
    private var pos:Int=0
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding=FragmentHistoryBinding.inflate(inflater, container, false)
        appDataBase= AppDataBase.getInstance(requireContext())
        list= ArrayList(appDataBase.recentDao().getAllRecent())
        historyAdapter= HistoryAdapter(list,object:HistoryAdapter.OnItemClickListener{
            override fun onClickMore(view: View, recentModel: RecentModel,position:Int) {
                obj=recentModel
                pos=position
                val popup = PopupMenu(requireContext(),view)
                popup.setOnMenuItemClickListener(this@HistoryFragment)
                popup.inflate(R.menu.menu_popup5)
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
        binding.recView.adapter=historyAdapter
        binding.searchViewHistory.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                historyAdapter.filter.filter(newText)
                return false
            }
        })
        binding.logo.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.icon.setOnClickListener {
            val popup = PopupMenu(requireContext(),it)
            popup.inflate(R.menu.menu_popup3)
            popup.show()
        }
        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.remove_from_history->{
               appDataBase.recentDao().removeRecent(obj!!)
                list.remove(obj)
                historyAdapter.notifyItemRemoved(pos)
                historyAdapter.notifyItemRangeChanged(pos,list.size)
            }
            else->{
                return false
            }
        }
        return false
    }
}