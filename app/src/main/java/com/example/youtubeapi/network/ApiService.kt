package com.example.youtubeapi.network

import com.example.youtubeapi.models.MyYoutubeData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getChannelVideos(
        //AIzaSyAHb-5CrbUgmZ7d4s7VHAaoepYZTsuLqAA
        //AIzaSyB7509nOKqurYyQapmEYnIFxRpfGIfLThQ
        //AIzaSyDcN0IuI0xkpxBxLDYNRmEq1Kyny6OEyzc
        @Query("key")key:String="AIzaSyB7509nOKqurYyQapmEYnIFxRpfGIfLThQ",
        @Query("channelId")channel:String="UCLRXXDGv3L_gGxUHFY8D45g",
        @Query("part")p:String="snippet",
        @Query("order")o:String="date",
        @Query("maxResults")m:Int=30,
    ):MyYoutubeData
}