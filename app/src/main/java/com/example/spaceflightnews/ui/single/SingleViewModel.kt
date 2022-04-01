package com.example.spaceflightnews.ui.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.data.model.Events
import com.example.spaceflightnews.data.model.Launches
import com.example.spaceflightnews.repository.SpaceflightRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SingleViewModel : ViewModel() {
    private val repository = SpaceflightRepository

    private val _singleArticle: MutableLiveData<Article> by lazy {
        MutableLiveData<Article>()
    }

    val singleArticle: LiveData<Article>
        get() = _singleArticle

    fun addArticle(article: Article) {
        _singleArticle.value = article
    }

    private val _eventArticles: MutableLiveData<List<Article>> by lazy {
        MutableLiveData<List<Article>>()
    }

    val eventArticles: LiveData<List<Article>>
        get() = _eventArticles

    private val _launchArticles: MutableLiveData<List<Article>> by lazy {
        MutableLiveData<List<Article>>()
    }

    val launchArticles: LiveData<List<Article>>
        get() = _launchArticles

    fun getLaunches(launches: List<Launches>) {
        val firstId = launches.first().id
        viewModelScope.launch {
            repository.getLaunches(firstId).collect {
                _launchArticles.value = it
            }
        }
    }

    fun getEvents(event: List<Events>) {
        val firstId = event.first().id
        viewModelScope.launch {
            repository.getEvents(firstId).collect {
                _eventArticles.value = it
            }
        }
    }
}