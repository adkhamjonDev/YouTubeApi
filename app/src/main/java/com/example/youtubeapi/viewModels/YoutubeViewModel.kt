package com.example.youtubeapi.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.models.MyYoutubeData
import com.example.youtubeapi.network.ApiService
import com.example.youtubeapi.repository.YoutubeRepository
import com.example.youtubeapi.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
class YoutubeViewModel(apiService: ApiService):ViewModel() {
    val youtubeLiveData=MutableLiveData<Resource<MyYoutubeData>>()
    private val youtubeRepository=YoutubeRepository(apiService)
    init {
        getVideos()
    }
    private fun getVideos() {
        viewModelScope.launch {
            youtubeLiveData.postValue(Resource.loading(null))
                youtubeRepository.getYoutubeData()
                    .catch {e->
                        youtubeLiveData.postValue(Resource.error(e.message?:"Error",null))
                    }.collect {
                        youtubeLiveData.postValue(Resource.success(it))
                    }
        }
    }
    @JvmName("getYoutubeLiveData1")
    fun getYoutubeLiveData():MutableLiveData<Resource<MyYoutubeData>>{
        return youtubeLiveData
    }
}