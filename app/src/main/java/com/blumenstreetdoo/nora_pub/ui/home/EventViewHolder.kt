package com.blumenstreetdoo.nora_pub.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.databinding.ItemEventBinding
import com.blumenstreetdoo.nora_pub.domain.models.Event

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemEventBinding.bind(itemView)

    fun bind(currentEvent: Event) {
        with(binding) {
            eventLabel.text = currentEvent.title
        }
    }
}
