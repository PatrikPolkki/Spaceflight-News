package com.example.spaceflightnews.ui.main

import com.example.spaceflightnews.data.model.Article

interface CellClickListener {
    fun onCellClickListener(article: Article)
}