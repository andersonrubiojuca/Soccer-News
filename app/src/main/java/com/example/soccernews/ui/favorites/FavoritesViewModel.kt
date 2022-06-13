package com.example.soccernews.ui.favorites

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soccernews.data.SoccerNewsRepository
import com.example.soccernews.domain.News
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {


    var news: LiveData<List<News>> = SoccerNewsRepository.getInstance().localDb.newsDao().loadFavoriteNews()


    fun saveNews(news: News){
        viewModelScope.launch {
            SoccerNewsRepository.getInstance().localDb.newsDao().save(news)
        }
    }
}