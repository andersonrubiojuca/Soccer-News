package com.example.soccernews.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class News(@PrimaryKey var id: Int,
                var title: String,
                var description: String,
                var image: String,
                var link: String,
                var favorite: Boolean
): Parcelable
