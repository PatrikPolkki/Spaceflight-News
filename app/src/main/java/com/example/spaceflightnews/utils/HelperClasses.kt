package com.example.spaceflightnews.utils

import com.example.spaceflightnews.data.model.Article

/**
 * Reduce size of related article list to 5 if it's larger 5
 */
fun filterSizeOfList(list: List<Article>): List<Article> {
    return if (list.size > 5) list.take(5) else list
}

/**
 * Filter same parent article out of related article list
 */
fun filterDuplicatesOut(list: List<Article>, id: Long?): List<Article> {
    return list.filter {
        it.id != id
    }
}