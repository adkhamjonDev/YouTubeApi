package com.example.youtubeapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.databinding.HistoryItemBinding
import com.example.youtubeapi.databinding.LibraryItemBinding
import com.example.youtubeapi.models.RecentModel
import com.squareup.picasso.Picasso

class HistoryAdapter(var list: ArrayList<RecentModel>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<HistoryAdapter.MyViewHolder>(),Filterable{
    val list1=ArrayList<RecentModel>(list)
    inner class MyViewHolder(var historyItemBinding: HistoryItemBinding):RecyclerView.ViewHolder(
        historyItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.historyItemBinding.tittle.text=list[position].videoName
        Picasso.get().load(list[position].image).into(holder.historyItemBinding.image)
        holder.historyItemBinding.more.setOnClickListener {
            onItemClickListener.onClickMore(holder.historyItemBinding.more,list[position],position)
        }
        holder.historyItemBinding.image.setOnClickListener {
            onItemClickListener.onClickItem(list[position])
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener {
        fun onClickMore(view: View, recentModel: RecentModel,position: Int)
        fun onClickItem(recentModel: RecentModel)
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }
    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val newList = ArrayList<RecentModel>()
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
            list.addAll(results.values as ArrayList<RecentModel>)
            notifyDataSetChanged()
        }
    }
}