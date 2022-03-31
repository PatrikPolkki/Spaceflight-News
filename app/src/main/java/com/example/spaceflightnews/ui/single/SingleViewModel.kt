package com.example.spaceflightnews.ui.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.data.model.Events
import com.example.spaceflightnews.data.model.Launches
import com.example.spaceflightnews.repository.SpaceflightRepository
import kotlinx.coroutines.async
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

    fun getLaunches(launches: List<Launches>) {
        viewModelScope.launch {
            for (l in launches) {
                val launch = async {

                }
            }
        }
    }

    fun getEvents(event: List<Events>) {
        viewModelScope.launch {
            for (e in event) {
                val event = async {
                }
            }

        }
    }
}