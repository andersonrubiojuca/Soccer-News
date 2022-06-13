package com.example.soccernews.ui.news

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.soccernews.data.SoccerNewsRepository
import com.example.soccernews.data.local.SoccerNewsDb
import com.example.soccernews.data.remote.SoccerNewsAPI
import com.example.soccernews.domain.News
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class NewsViewModel : ViewModel() {

    enum class State {
        DOING, DONE, ERROR
    }


    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>>
        get() = _news

    val state = MutableLiveData<State>()

    init {


        findNews()
    }

    fun saveNews(news: News){
        viewModelScope.launch{
            SoccerNewsRepository.getInstance().localDb.newsDao().save(news)
        }
    }

    fun getNews(): List<News> {
        return _news.value!!
    }

    fun findNews() {
        state.value = State.DOING
        SoccerNewsRepository.getInstance().remoteApi.getNews().enqueue(object : Callback<List<News>> {
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                if (response.isSuccessful) {
                    state.value = State.DONE
                    _news.value = response.body()
                } else {
                    state.value = State.ERROR
                    _news.value = listOf(News(0, "", "", "", "", false))
                }
            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {
                state.value = State.ERROR
                listOf(News(0, "", "", "", "", false))
            }

        })
    }
}