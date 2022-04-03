package com.example.spaceflightnews.data.repository

import android.util.Log
import com.example.spaceflightnews.data.api.RetrofitInstance
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.utils.Recourse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

object SpaceflightRepository {
    private val retrofitInstance = RetrofitInstance
    private val serviceCall = retrofitInstance.service

    fun getArticles(limit: Int): Flow<Recourse<List<Article>>> = flow {
        try {
            emit(Recourse.Loading())
            val articleList = serviceCall.getArticles(limit)
            emit(Recourse.Success(articleList))
        } catch (e: HttpException) {
            emit(Recourse.Error(e.localizedMessage ?: "An unexpected error."))
        } catch (e: IOException) {
            emit(Recourse.Error("Couldn't reach server."))
        }
    }.flowOn(Dispatchers.IO)

    fun getEvents(id: Long): Flow<List<Article>> = flow {
        try {
            val eventList = serviceCall.getEvents(id)
            emit(eventList)
        } catch (e: HttpException) {
            e.message?.let { Log.e("HttpException", it) }
        } catch (e: IOException) {
            e.message?.let { Log.e("IOException", it) }
        }
    }.flowOn(Dispatchers.IO)


    fun getLaunches(id: String): Flow<List<Article>> = flow {
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