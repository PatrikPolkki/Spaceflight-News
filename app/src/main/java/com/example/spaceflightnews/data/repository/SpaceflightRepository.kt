package com.example.spaceflightnews.data.repository

import android.util.Log
import com.example.spaceflightnews.data.api.RetrofitInstance
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.utils.Recourse
import com.example.spaceflightnews.utils.filterSizeOfList
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
            Log.e("HttpException", e.message ?: "An unexpected error.")
            emit(Recourse.Error(e.localizedMessage ?: "An unexpected error."))
        } catch (e: IOException) {
            Log.e("IOExpection", e.message ?: "Couldn't reach server.")
            emit(Recourse.Error("Couldn't reach server."))
        }
    }.flowOn(Dispatchers.IO)

    fun getEvents(id: Long): Flow<Recourse<List<Article>>> = flow {
        try {
            emit(Recourse.Loading())
            val eventList = serviceCall.getEvents(id)
            val filteredList = filterSizeOfList(eventList)
            emit(Recourse.Success(filteredList))
        } catch (e: HttpException) {
            Log.e("HttpException", e.message ?: "An unexpected error.")
            emit(Recourse.Error(e.localizedMessage ?: "An unexpected error."))
        } catch (e: IOException) {
            Log.e("IOExpection", e.message ?: "Couldn't reach server.")
            emit(Recourse.Error("Couldn't reach server."))
        }
    }.flowOn(Dispatchers.IO)

    fun getLaunches(id: String): Flow<Recourse<List<Article>>> = flow {
        try {
            emit(Recourse.Loading())
            val launchList = serviceCall.getLaunches(id)
            val filteredList = filterSizeOfList(launchList)
            emit(Recourse.Success(filteredList))
        } catch (e: HttpException) {
            Log.e("HttpException", e.message ?: "An unexpected error.")
            emit(Recourse.Error(e.localizedMessage ?: "An unexpected error."))
        } catch (e: IOException) {
            Log.e("IOExpection", e.message ?: "Couldn't reach server.")
            emit(Recourse.Error("Couldn't reach server."))
        }
    }.flowOn(Dispatchers.IO)
}