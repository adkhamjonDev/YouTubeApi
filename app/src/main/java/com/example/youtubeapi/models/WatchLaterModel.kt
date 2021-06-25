package com.example.youtubeapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "watchlater")
class WatchLaterModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var videoName:String?=null,
    var videoDescription:String?=null,
    var videoId:String?=null,
    var image:String?=null)