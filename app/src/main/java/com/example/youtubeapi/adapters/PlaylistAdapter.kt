package com.example.youtubeapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.databinding.LibraryItemBinding
import com.example.youtubeapi.databinding.PlaylistItemBinding
import com.example.youtubeapi.models.PlaylistModel
import com.squareup.picasso.Picasso

class PlaylistAdapter(var list: List<PlaylistModel>):
    RecyclerView.Adapter<PlaylistAdapter.MyViewHolder>(){
    inner class MyViewHolder(var playlistItemBinding: PlaylistItemBinding):RecyclerView.ViewHolder(
        playlistItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PlaylistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.playlistItemBinding.name.text=list[position].name
    }
    override fun getItemCount(): Int {
        return list.size
    }
}