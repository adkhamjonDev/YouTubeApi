package com.example.youtubeapi.room
import androidx.room.*
import com.example.youtubeapi.models.CommentModel
import com.example.youtubeapi.models.PlaylistModel
import com.example.youtubeapi.models.WatchLaterModel

@Dao
interface PlaylistDao {
    @Insert
    fun addPlaylist(playlistModel: PlaylistModel)
    @Query("SELECT * FROM playlist")
    fun getAllPlaylist():List<PlaylistModel>
    @Delete
    fun removePlaylist(playlistModel: PlaylistModel)
    @Update
    fun editPlaylist(playlistModel: PlaylistModel)
}