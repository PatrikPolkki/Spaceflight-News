package com.example.spaceflightnews.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.data.repository.SpaceflightRepository
import com.example.spaceflightnews.ui.ArticleListState
import com.example.spaceflightnews.utils.Recourse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
    private val repository = SpaceflightRepository

    private val _singleArticle: MutableLiveData<Article> by lazy {
        MutableLiveData<Article>()
    }

    val singleArticle: LiveData<Article>
        get() = _singleArticle

    fun addArticleToViewModel(article: Article) {
        _singleArticle.value = article
    }

    private val _eventArticles: MutableLiveData<ArticleListState> by lazy {
        MutableLiveData<ArticleListState>()
    }

    val eventArticles: LiveData<ArticleListState>
        get() = _eventArticles

    private val _launchArticles: MutableLiveData<ArticleListState> by lazy {
        MutableLiveData<ArticleListState>()
    }

    val launchArticles: LiveData<ArticleListState>
        get() = _launchArticles

    fun getLaunches(id: String) {
        viewModelScope.launch {
            repository.getLaunches(id).collect { result ->
                when (result) {
                    is Recourse.Success -> {
                        _launchArticles.value =
                            ArticleListState(articles = result.data ?: emptyList())
                    }
                    is Recourse.Error -> {
                        _launchArticles.value =
                            ArticleListState(error = result.massage ?: "An unexpected error")
                    }
                    is Recourse.Loading -> {
                        _launchArticles.value = ArticleListState(isLoading = true)
                    }
                }
            }
        }
    }

    fun getEvents(id: Long) {
        viewModelScope.launch {
            repository.getEvents(id).collect { result ->
                when (result) {
                    is Recourse.Success -> {
                        _eventArticles.value =
                            ArticleListState(articles = result.data ?: emptyList())
                    }
                    is Recourse.Error -> {
                        _eventArticles.value =
                            ArticleListState(error = result.massage ?: "An unexpected error")
                    }
                    is Recourse.Loading -> {
                        _eventArticles.value = ArticleListState(isLoading = true)
                    }
                }
            }
        }
    }
}