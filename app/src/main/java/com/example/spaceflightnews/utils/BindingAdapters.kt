package com.example.spaceflightnews.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.spaceflightnews.data.model.Article
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setImage")
fun bindImage(image: ImageView, article: Article) {
    Glide.with(image)
        .load(article.imageUrl)
        .centerCrop()
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