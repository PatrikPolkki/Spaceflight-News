package com.example.spaceflightnews.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceflightnews.data.repository.SpaceflightRepository
import com.example.spaceflightnews.ui.ArticleListState
import com.example.spaceflightnews.utils.Recourse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = SpaceflightRepository

    private val _articleResult: MutableLiveData<ArticleListState> by lazy {
        MutableLiveData<ArticleListState>()
    }
    val articleResults: LiveData<ArticleListState>
        get() = _articleResult

    init {
        getArticles(20)
    }

    fun getArticles(limit: Int) {
        viewModelScope.launch {
            repository.getArticles(limit).collect { result ->
                when (result) {
                    is Recourse.Success -> {
                        _articleResult.value =
                            ArticleListState(articles = result.data ?: emptyList())
                    }
                    is Recourse.Error -> {
                        _articleResult.value =
                            ArticleListState(error = result.massage ?: "An unexpected error")
                    }
                    is Recourse.Loading -> {
                        _articleResult.value = ArticleListState(isLoading = true)
                    }
                }
            }
        }
    }
}