package com.blumenstreetdoo.nora_pub.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.ItemEventBinding
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.bumptech.glide.Glide

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemEventBinding.bind(itemView)

    fun bind(currentEvent: Event) {
        with(binding) {
            eventTitle.text = currentEvent.title
            eventDate.text = currentEvent.dateTime
            if (currentEvent.imageUrl != null) {
                Glide.with(eventImage.context)
                    .load(currentEvent.imageUrl)
                    .placeholder(R.drawable.placeholder_nora)
                    .into(eventImage)
            } else {
                eventImage.setImageResource(R.drawable.placeholder_nora)
            }
        }
    }
}
