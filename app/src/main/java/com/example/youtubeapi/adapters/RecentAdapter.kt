package com.example.youtubeapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.databinding.LibraryItemBinding
import com.example.youtubeapi.models.RecentModel
import com.squareup.picasso.Picasso

class RecentAdapter(var list: List<RecentModel>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<RecentAdapter.MyViewHolder>(){
    inner class MyViewHolder(var libraryItemBinding: LibraryItemBinding):RecyclerView.ViewHolder(
        libraryItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LibraryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.libraryItemBinding.tittle.text=list[position].videoName
        Picasso.get().load(list[position].image).into(holder.libraryItemBinding.image)
        holder.libraryItemBinding.more.setOnClickListener {
            onItemClickListener.onClickMore(holder.libraryItemBinding.more,list[position],position)
        }
        holder.libraryItemBinding.image.setOnClickListener {
            onItemClickListener.onClickItem(list[position])
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener {
        fun onClickMore(view: View, recentModel: RecentModel,position:Int)
        fun onClickItem(recentModel: RecentModel)
    }
}