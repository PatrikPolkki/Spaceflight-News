package com.example.spaceflightnews.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceflightnews.data.model.Articles
import com.example.spaceflightnews.repository.SpaceflightRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = SpaceflightRepository

    private val mArticleResult: MutableLiveData<List<Articles>> by lazy {
        MutableLiveData<List<Articles>>().also {
            it.value = listOf()
        }
    }
    val articleResults: LiveData<List<Articles>>
        get() = mArticleResult

    init {
        testi()
    }

    private fun testi() {
        viewModelScope.launch {
            repository.getArticles(7).collect {
                mArticleResult.value = it
            }
        }
    }
}