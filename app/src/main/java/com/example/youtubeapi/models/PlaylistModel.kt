package com.example.youtubeapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "playlist")
data class PlaylistModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var name:String?=null,
    var description:String?=null
)
