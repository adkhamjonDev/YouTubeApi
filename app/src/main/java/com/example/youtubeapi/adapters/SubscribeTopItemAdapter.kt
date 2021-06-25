package com.example.youtubeapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.databinding.SubscribeTopItemBinding
import com.example.youtubeapi.models.SubscribeItemModel
class SubscribeTopItemAdapter(var list: List<SubscribeItemModel>):
    RecyclerView.Adapter<SubscribeTopItemAdapter.MyViewHolder>(){
    inner class MyViewHolder(var subscribeTopItemBinding: SubscribeTopItemBinding):RecyclerView.ViewHolder(
        subscribeTopItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SubscribeTopItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        holder.subscribeTopItemBinding.image.setImageResource(obj.image!!)
        holder.subscribeTopItemBinding.tittle.text=obj.name
    }
    override fun getItemCount(): Int {
        return list.size
    }
}