package com.example.spaceflightnews.ui.articleList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceflightnews.data.model.Article
import com.example.spaceflightnews.databinding.ArticleItemBinding
import com.example.spaceflightnews.ui.ArticleClickListener

class ArticleListAdapter(private val articleClickListener: ArticleClickListener) :
    ListAdapter<Article, ArticleListAdapter.ViewHolder>(DiffCallback) {

    // Handles a changes in incoming list
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articleItem = getItem(position)
        holder.binding.articleItem = articleItem

        holder.itemView.setOnClickListener {
            articleClickListener.onArticleClickListener(articleItem)
        }
    }
}