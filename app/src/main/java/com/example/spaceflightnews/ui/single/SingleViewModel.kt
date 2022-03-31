package com.example.spaceflightnews.ui.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceflightnews.data.model.Article

class SingleViewModel : ViewModel() {

    private val _singleArticle: MutableLiveData<Article> by lazy {
        MutableLiveData<Article>()
    }

    val singleArticle: LiveData<Article>
        get() = _singleArticle

    fun addArticle(article: Article) {
        _singleArticle.value = article
    }
}