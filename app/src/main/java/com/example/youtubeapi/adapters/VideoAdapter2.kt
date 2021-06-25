package com.example.youtubeapi.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.R
import com.example.youtubeapi.databinding.SubscribeItemBinding
import com.example.youtubeapi.databinding.YoutubeItemBinding
import com.example.youtubeapi.models.Item
import com.squareup.picasso.Picasso
class VideoAdapter2(var listener: OnItemClickListener) :
    ListAdapter<Item, VideoAdapter2.Vh>(MyDiffUtill()) {
    inner class Vh(var subscribeItemBinding: SubscribeItemBinding) :
        RecyclerView.ViewHolder(subscribeItemBinding.root) {
        fun onBind(item: Item) {
            Picasso.get().load(item.snippet.thumbnails.high.url)
                .placeholder(R.drawable.loading_thumbnail)
                .error(R.drawable.no_thumbnail)
                .into(subscribeItemBinding.image)
            subscribeItemBinding.tittle.text = item.snippet.title
                subscribeItemBinding.channelName.text = "PDP Academy  - Premieres 6/12/21  10:00 PM"
            subscribeItemBinding.image.setOnClickListener {
                listener.onItemClick(item)
            }
            subscribeItemBinding.more.setOnClickListener {
                listener.onItemClickMore(item,subscribeItemBinding.more)
            }
            subscribeItemBinding.card.setOnClickListener {
                listener.onItemClickReminder()
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(SubscribeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        fun onItemClickReminder()
    }
}
