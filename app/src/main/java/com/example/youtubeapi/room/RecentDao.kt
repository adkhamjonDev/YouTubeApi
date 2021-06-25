package com.example.youtubeapi.room
import androidx.room.*
import com.example.youtubeapi.models.CommentModel
import com.example.youtubeapi.models.RecentModel

@Dao
interface RecentDao {
    @Insert
    fun addRecent(recentModel: RecentModel)
    @Query("SELECT * FROM recent")
    fun getAllRecent():List<RecentModel>
    @Delete
    fun removeRecent(recentModel: RecentModel)
}