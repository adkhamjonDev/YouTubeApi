package com.example.youtubeapi.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.network.ApiService
import java.lang.IllegalArgumentException

class ViewModelFactory(var apiService: ApiService):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(YoutubeViewModel::class.java)){
            return YoutubeViewModel(apiService) as T
        }
        throw IllegalArgumentException("Error view model")
    }
}