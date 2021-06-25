package com.example.youtubeapi.room
import androidx.room.*
import com.example.youtubeapi.models.CommentModel
@Dao
interface CommentDao {
    @Insert
    fun addComment(commentModel: CommentModel)
    @Query("SELECT * FROM comments where videoName=:str")
    fun getAllComments(str:String):List<CommentModel>
    @Delete
    fun removeComment(commentModel: CommentModel)
    @Update
    fun editComment(commentModel: CommentModel)
}