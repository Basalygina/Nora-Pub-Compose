package com.blumenstreetdoo.nora_pub.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.ItemNewsBinding
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.NewsType
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemNewsBinding.bind(itemView)

    fun bind(currentNews: News) {
        with(binding) {
            newsType.visibility = if (currentNews.type == NewsType.NEW_ARRIVAL) View.VISIBLE else View.GONE
            newsTitle.text = currentNews.title
            if (currentNews.imageUrl != null) {
                Glide.with(newsImage.context)
                    .load(currentNews.imageUrl)
                    .transform(RoundedCorners(8))
                    .placeholder(R.drawable.placeholder_nora_large)
                    .into(newsImage)
            } else {
                newsImage.setImageResource(R.drawable.placeholder_nora_large)
            }
        }
    }
}
