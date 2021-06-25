package com.example.youtubeapi.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.R
import com.example.youtubeapi.databinding.YoutubeItemBinding
import com.example.youtubeapi.models.Item
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class VideoAdapter(var listener: OnItemClickListener) :
    ListAdapter<Item, VideoAdapter.Vh>(MyDiffUtill())  {
    inner class Vh(var youtubeItemBinding: YoutubeItemBinding) :
        RecyclerView.ViewHolder(youtubeItemBinding.root) {
        fun onBind(item: Item) {
            Picasso.get().load(item.snippet.thumbnails.high.url)
                .placeholder(R.drawable.loading_thumbnail)
                .error(R.drawable.no_thumbnail)
                .into(youtubeItemBinding.image)
            youtubeItemBinding.tittle.text = item.snippet.title
                youtubeItemBinding.channelName.text = "PDP Academy ${item.snippet.publishTime}"
            youtubeItemBinding.image.setOnClickListener {
                listener.onItemClick(item)
            }
            youtubeItemBinding.more.setOnClickListener {
                listener.onItemClickMore(item,youtubeItemBinding.more)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(YoutubeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
    class MyDiffUtill : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Item)
        fun onItemClickMore(item: Item,view: View)
    }
}
