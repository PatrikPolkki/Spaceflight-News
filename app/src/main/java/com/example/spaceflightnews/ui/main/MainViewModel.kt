package com.example.spaceflightnews.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.repository.SpaceflightRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = SpaceflightRepository

    private val _articleResult: MutableLiveData<List<Article>> by lazy {
        MutableLiveData<List<Article>>().also {
            it.value = listOf()
        }
    }
    val articleResults: LiveData<List<Article>>
        get() = _articleResult

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            repository.getArticles(7).collect {
                _articleResult.value = it
            }
        }
    }
}