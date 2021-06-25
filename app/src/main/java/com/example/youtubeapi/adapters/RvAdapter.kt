package com.example.youtubeapi.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.R
import com.example.youtubeapi.databinding.YoutubeItemBinding
import com.example.youtubeapi.models.Item
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList
class RvAdapter(var list: ArrayList<Item>, var listener: OnItemClickListener):
    RecyclerView.Adapter<RvAdapter.MyViewHolder>(),Filterable {
    val list1=ArrayList<Item>(list)
    inner class MyViewHolder(var youtubeItemBinding: YoutubeItemBinding): RecyclerView.ViewHolder(
        youtubeItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            YoutubeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        Picasso.get().load(obj.snippet.thumbnails.high.url)
            .placeholder(R.drawable.loading_thumbnail)
            .error(R.drawable.no_thumbnail)
            .into(holder.youtubeItemBinding.image)
        holder.youtubeItemBinding.tittle.text = obj.snippet.title
        holder.youtubeItemBinding.channelName.text = "PDP Academy ${obj.snippet.publishTime}"
        holder.youtubeItemBinding.image.setOnClickListener {
            listener.onItemClick(obj)
        }
        holder.youtubeItemBinding.more.setOnClickListener {
            listener.onItemClickMore(obj,holder.youtubeItemBinding.more)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener {
        fun onItemClick(item: Item)
        fun onItemClickMore(item: Item,view: View)
    }
    override fun getFilter(): Filter {
        return exampleFilter
    }
    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val newList=ArrayList<Item>()
            if(constraint==null|| constraint.isEmpty()){
                newList.addAll(list1)
            }else{
                val filterPattern= constraint.toString().lowercase(Locale.getDefault()).trim()
                for (i in 0 until list1.size){
                    if(list1[i].snippet.title.lowercase(Locale.getDefault()).contains(filterPattern)){
                        newList.add(list1[i])
                    }
                }
            }
            val results=FilterResults()
            results.values=newList
            return results
        }
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            list.clear()
            list.addAll(results.values as ArrayList<Item>)
            notifyDataSetChanged()
        }
    }
}