package com.blumenstreetdoo.nora_pub.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.databinding.ItemNewsBinding
import com.blumenstreetdoo.nora_pub.domain.models.News

class NewsAdapter(
    private val news: List<News>,
    private val clickListener: NewsClickListener,
) : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = news[position]
        holder.bind(currentNews)
        holder.itemView.setOnClickListener { clickListener.onNewsClick(currentNews) }
    }

    override fun getItemCount() = news.size

    fun updateNews(newNews: List<News>) {
        (news as MutableList).clear()
        news.addAll(newNews)
        notifyDataSetChanged()
    }

    fun interface NewsClickListener {
        fun onNewsClick(news: News)
    }
}
