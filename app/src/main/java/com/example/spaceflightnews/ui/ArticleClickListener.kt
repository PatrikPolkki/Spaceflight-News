package com.example.spaceflightnews.ui

import com.example.spaceflightnews.data.model.Article

interface ArticleClickListener {
    fun onArticleClickListener(article: Article)
}