package com.example.soccernews.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.soccernews.domain.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM news WHERE favorite = 1")
    fun loadFavoriteNews() : LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(news: News)
}