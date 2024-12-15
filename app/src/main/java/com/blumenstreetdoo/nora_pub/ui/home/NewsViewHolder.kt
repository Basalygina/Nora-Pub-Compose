package com.blumenstreetdoo.nora_pub.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.databinding.ItemNewsBinding
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.NewsType

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemNewsBinding.bind(itemView)

    fun bind(currentNews: News) {
        with(binding) {
            if (currentNews.type == NewsType.NEW_ARRIVAL)
            newsTitle.text = currentNews.title
        }
    }
}
