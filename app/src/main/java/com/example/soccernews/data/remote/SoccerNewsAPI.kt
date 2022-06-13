package com.example.soccernews.data.remote

import com.example.soccernews.domain.News
import retrofit2.Call
import retrofit2.http.GET


interface SoccerNewsAPI {

    @GET("news.json")
    fun getNews(): Call<List<News>>
}