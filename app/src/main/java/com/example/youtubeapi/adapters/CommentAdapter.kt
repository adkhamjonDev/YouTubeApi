package com.example.youtubeapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.databinding.CommentItemBinding
import com.example.youtubeapi.models.CommentModel
class CommentAdapter(var list: List<CommentModel>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<CommentAdapter.MyViewHolder>(){
    inner class MyViewHolder(var commentItemBinding: CommentItemBinding):RecyclerView.ViewHolder(
        commentItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CommentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        holder.commentItemBinding.text.text=obj.text

        holder.commentItemBinding.l.setOnClickListener {
            onItemClickListener.onClickLike(holder.commentItemBinding.like)
        }
        holder.commentItemBinding.u.setOnClickListener {
            onItemClickListener.onClickLike(holder.commentItemBinding.dislike)
        }
        holder.commentItemBinding.remove.setOnClickListener {
            onItemClickListener.onRemoveComment(obj,position)
        }
        holder.commentItemBinding.edit.setOnClickListener {
            onItemClickListener.onClickEdit(obj,position)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener {
        fun onRemoveComment(commentModel: CommentModel,position:Int)
        fun onClickLike(textView: TextView)
        fun onClickDislike(textView: TextView)
        fun onClickEdit(commentModel: CommentModel,position:Int)
    }
}