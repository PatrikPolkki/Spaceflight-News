package com.example.spaceflightnews.repository

import android.util.Log
import com.example.spaceflightnews.data.api.RetrofitInstance
import com.example.spaceflightnews.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

object SpaceflightRepository {
    private val retrofitInstance = RetrofitInstance
    private val serviceCall = retrofitInstance.service

    suspend fun getArticles(limit: Int): Flow<List<Article>> {
        return flow {
            try {
                val articleList = serviceCall.getArticles(limit)
                emit(articleList)
            } catch (e: HttpException) {
                e.message?.let { Log.e("HttpException", it) }
            } catch (e: IOException) {
                e.message?.let { Log.e("IOException", it) }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getEvents(id: Long): Flow<List<Article>> {
        return flow {
            try {
                val eventList = serviceCall.getEvents(id)
                emit(eventList)
            } catch (e: HttpException) {
                e.message?.let { Log.e("HttpException", it) }
            } catch (e: IOException) {
                e.message?.let { Log.e("IOException", it) }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getLaunches(id: String): Flow<List<Article>> {
        return flow {
            try {
                val launchList = serviceCall.getLaunches(id)
                emit(launchList)
            } catch (e: HttpException) {
                e.message?.let { Log.e("HttpException", it) }
            } catch (e: IOException) {
                e.message?.let { Log.e("IOException", it) }
            }
        }.flowOn(Dispatchers.IO)
    }
}