package com.example.youtubeapi.room
import androidx.room.*
import com.example.youtubeapi.models.CommentModel
import com.example.youtubeapi.models.WatchLaterModel

@Dao
interface WatchLaterDao {
    @Insert
    fun addWatchLater(watchLaterModel: WatchLaterModel)
    @Query("SELECT * FROM watchlater")
    fun getAllWatchLater():List<WatchLaterModel>
    @Delete
    fun removeWatchLater(watchLaterModel: WatchLaterModel)
    @Update
    fun editWatchLater(watchLaterModel: WatchLaterModel)
}