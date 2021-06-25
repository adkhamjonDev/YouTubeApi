package com.example.youtubeapi.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.databinding.ChannelListItemBinding
import com.example.youtubeapi.models.SubscribeItemModel
class ChannelListAdapter(var list: List<SubscribeItemModel>):
    RecyclerView.Adapter<ChannelListAdapter.MyViewHolder>(){
    inner class MyViewHolder(var channelListItemBinding: ChannelListItemBinding):RecyclerView.ViewHolder(
        channelListItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ChannelListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        holder.channelListItemBinding.image.setImageResource(obj.image!!)
        holder.channelListItemBinding.tittle.text=obj.name
        if(position%3==0){
            holder.channelListItemBinding.card.setCardBackgroundColor(Color.parseColor("#76A3CB"))
        }else{
            holder.channelListItemBinding.card.setCardBackgroundColor(Color.WHITE)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}