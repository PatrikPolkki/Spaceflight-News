package com.example.spaceflightnews.data.model

data class Articles(
    val id: Long,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String,
    val featured: Boolean,
    val launches: List<Launches>?,
    val events: List<Events>?
)

data class Launches(
    val id: String,
    val provider: String
)

data class Events(
    val id: Long,
    val provider: String
)