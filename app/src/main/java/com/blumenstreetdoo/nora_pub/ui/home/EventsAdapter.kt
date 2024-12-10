package com.blumenstreetdoo.nora_pub.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.databinding.ItemEventBinding
import com.blumenstreetdoo.nora_pub.domain.models.Event

class EventsAdapter(private val events: List<Event>,
                    private val clickListener: EventClickListener,
) : RecyclerView.Adapter<EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentEvent = events[position]
        holder.bind(currentEvent)
        holder.itemView.setOnClickListener { clickListener.onEventClick(currentEvent) }
    }

    override fun getItemCount() = events.size
}

fun interface EventClickListener {
    fun onEventClick(event: Event)
}