package com.example.spaceflightnews.ui

import com.example.spaceflightnews.data.model.Article

data class ArticleListState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String = ""
)