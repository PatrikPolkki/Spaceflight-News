package com.example.spaceflightnews.repository

import com.example.spaceflightnews.data.api.RetrofitInstance
import com.example.spaceflightnews.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object SpaceflightRepository {
    private val retrofitInstance = RetrofitInstance
    private val serviceCall = retrofitInstance.service

    suspend fun getArticles(limit: Int): Flow<List<Article>> {
        return flow {
            val articleList = serviceCall.getArticles(limit)
            emit(articleList)
        }.flowOn(Dispatchers.IO)
    }
}