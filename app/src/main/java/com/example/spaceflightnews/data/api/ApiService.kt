package com.example.spaceflightnews.data.api

import com.example.spaceflightnews.data.model.Articles
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("articles")
    suspend fun getArticles(
        @Query("_limit") limit: Int
    ): List<Articles>
}