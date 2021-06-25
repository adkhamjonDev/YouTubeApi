package com.example.youtubeapi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.youtubeapi.models.CommentModel
import com.example.youtubeapi.models.PlaylistModel
import com.example.youtubeapi.models.RecentModel
import com.example.youtubeapi.models.WatchLaterModel

@Database(entities = [CommentModel::class,RecentModel::class,WatchLaterModel::class,PlaylistModel::class],version = 2)
abstract class AppDataBase:RoomDatabase() {
    abstract fun commentDao():CommentDao
    abstract fun recentDao():RecentDao
    abstract fun watchLaterDao():WatchLaterDao
    abstract fun playlistDao():PlaylistDao
    companion object{
        private var appDatabase: AppDataBase? = null
        @Synchronized
        fun getInstance(context: Context): AppDataBase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context, AppDataBase::class.java, "my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase!!
        }
    }
}