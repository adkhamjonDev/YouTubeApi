package com.example.youtubeapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.databinding.WatchLaterBinding
import com.example.youtubeapi.models.WatchLaterModel
import com.squareup.picasso.Picasso

class WatchLaterAdapter(var list: ArrayList<WatchLaterModel>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<WatchLaterAdapter.MyViewHolder>(),Filterable{
    val list1=ArrayList<WatchLaterModel>(list)
    inner class MyViewHolder(var watchLaterBinding: WatchLaterBinding):RecyclerView.ViewHolder(
        watchLaterBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            WatchLaterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.watchLaterBinding.tittle.text=list[position].videoName
        Picasso.get().load(list[position].image).into(holder.watchLaterBinding.image)
        holder.watchLaterBinding.more.setOnClickListener {
            onItemClickListener.onClickMore(holder.watchLaterBinding.more,list[position],position)
        }
        holder.watchLaterBinding.image.setOnClickListener {
            onItemClickListener.onClickItem(list[position])
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener {
        fun onClickMore(view: View, watchLaterModel: WatchLaterModel,position: Int)
        fun onClickItem(watchLaterModel: WatchLaterModel)
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }
    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val newList = ArrayList<WatchLaterModel>()
            if (constraint == null || constraint.isEmpty()) {
                newList.addAll(list1)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (i in 0 until list1.size) {
                    if (list1[i].videoName?.toLowerCase()!!.contains(filterPattern)) {
                        newList.add(list1[i])
                    }
                }
            }
            val results = FilterResults()
            results.values = newList
            return results
        }
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            list.clear()
            list.addAll(results.values as ArrayList<WatchLaterModel>)
            notifyDataSetChanged()
        }
    }
}