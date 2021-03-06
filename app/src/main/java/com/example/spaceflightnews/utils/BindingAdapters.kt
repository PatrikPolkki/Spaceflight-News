package com.example.spaceflightnews.utils

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.spaceflightnews.R
import com.example.spaceflightnews.data.model.Article
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setImage")
fun bindImage(image: ImageView, article: Article) {
    Glide.with(image)
        .load(article.imageUrl)
        .centerCrop()
        .error(R.drawable.ic_baseline_image_not_supported_24)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(image)
}

@BindingAdapter("date")
fun bindDate(view: TextView, article: Article) {

    val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val newFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH)

    val date: Date? = oldFormat.parse(article.publishedAt)
    val formattedDate = date?.let {
        newFormat.format(it)
    }

    view.text = formattedDate ?: ""
}

@BindingAdapter("loading")
fun bindLoading(view: View, isLoading: Boolean) {
    when (view) {
        is ProgressBar -> {
            view.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        else -> {
            view.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }
}

@BindingAdapter("articlesVisibility")
fun bindArticleVisibility(view: LinearLayout, articleList: List<Article>?) {
    if (articleList != null && articleList.isNotEmpty()) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("noArticles")
fun bindNoArticles(view: View, article: Article) {
    when (view) {
        is TextView -> {
            view.visibility =
                if (article.events.isEmpty() && article.launches.isEmpty()) View.VISIBLE else View.GONE
        }
        else -> {
            view.visibility =
                if (article.events.isEmpty() && article.launches.isEmpty()) View.GONE else View.VISIBLE
        }
    }
}