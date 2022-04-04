package com.example.spaceflightnews.utils

import com.example.spaceflightnews.data.model.Article
import org.junit.Assert.assertEquals
import org.junit.Test

class HelperClassesKtTest {

    @Test
    fun `reduce size of list to 5`() {
        val list = mutableListOf<Article>()
        ('a'..'z').forEachIndexed { index, i ->
            list.add(
                Article(
                    id = index.toLong(),
                    title = i.toString(),
                    url = i.toString(),
                    imageUrl = i.toString(),
                    newsSite = i.toString(),
                    summary = i.toString(),
                    publishedAt = i.toString(),
                    updatedAt = i.toString(),
                    featured = false,
                    launches = emptyList(),
                    events = emptyList()
                )
            )
        }
        val filteredList = filterSizeOfList(list)
        assertEquals(5, filteredList.size)
    }

    @Test
    fun `keep the list size when it's less than 5`() {
        val list = mutableListOf<Article>()
        ('a'..'d').forEachIndexed { index, i ->
            list.add(
                Article(
                    id = index.toLong(),
                    title = i.toString(),
                    url = i.toString(),
                    imageUrl = i.toString(),
                    newsSite = i.toString(),
                    summary = i.toString(),
                    publishedAt = i.toString(),
                    updatedAt = i.toString(),
                    featured = false,
                    launches = emptyList(),
                    events = emptyList()
                )
            )
        }
        val filteredList = filterSizeOfList(list)
        assertEquals(4, filteredList.size)
    }

    @Test
    fun `filter same article out of related articles`() {
        val list = mutableListOf<Article>()
        ('a'..'d').forEachIndexed { index, i ->
            list.add(
                Article(
                    id = index.toLong(),
                    title = i.toString(),
                    url = i.toString(),
                    imageUrl = i.toString(),
                    newsSite = i.toString(),
                    summary = i.toString(),
                    publishedAt = i.toString(),
                    updatedAt = i.toString(),
                    featured = false,
                    launches = emptyList(),
                    events = emptyList()
                )
            )
        }
        val filteredList = filterDuplicatesOut(list, list.first().id)
        assertEquals(list.takeLast(3), filteredList)
    }

    @Test
    fun `duplicate article not found in related articles`() {
        val list = mutableListOf<Article>()
        ('a'..'d').forEachIndexed { index, i ->
            list.add(
                Article(
                    id = index.toLong(),
                    title = i.toString(),
                    url = i.toString(),
                    imageUrl = i.toString(),
                    newsSite = i.toString(),
                    summary = i.toString(),
                    publishedAt = i.toString(),
                    updatedAt = i.toString(),
                    featured = false,
                    launches = emptyList(),
                    events = emptyList()
                )
            )
        }
        val filteredList = filterDuplicatesOut(list, 20L)
        assertEquals(list, filteredList)
    }
}