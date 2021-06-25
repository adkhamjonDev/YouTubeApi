package com.example.youtubeapi.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {
    const val BASE_URL="https://www.googleapis.com/youtube/v3/"

    fun getRetrofit():Retrofit{

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService=ApiClient.getRetrofit().create(ApiService::class.java)
}