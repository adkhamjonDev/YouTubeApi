package com.example.youtubeapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var videoName:String?=null,
    var text:String?=null)