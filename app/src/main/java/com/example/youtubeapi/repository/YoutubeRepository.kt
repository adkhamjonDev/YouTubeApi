package com.example.youtubeapi.repository
import com.example.youtubeapi.network.ApiService
import kotlinx.coroutines.flow.flow

class YoutubeRepository(var apiService: ApiService) {
    suspend fun getYoutubeData()= flow { emit(apiService.getChannelVideos()) }
}