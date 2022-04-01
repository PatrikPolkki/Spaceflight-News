package com.example.spaceflightnews.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Long,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String,
    val featured: Boolean,
    val launches: List<Launches>,
    val events: List<Events>
) : Parcelable

@Parcelize
data class Launches(
    val id: String,
    val provider: String
) : Parcelable

@Parcelize
data class Events(
    val id: Long,
    val provider: String
) : Parcelable