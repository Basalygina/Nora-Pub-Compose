package com.blumenstreetdoo.nora_pub.ui.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.DetailsEventBinding
import com.bumptech.glide.Glide

class EventDetailsFragment : Fragment() {
    private val args: EventDetailsFragmentArgs by navArgs()
    private var _binding: DetailsEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentEvent = args.event
        with(binding) {
            eventTitle.text = currentEvent.title
            eventDate.text = currentEvent.dateTime
            eventDescription.text = currentEvent.description
            if (currentEvent.imageUrl != null) {
                Glide.with(eventImage.context)
                    .load(currentEvent.imageUrl)
                    .placeholder(R.drawable.placeholder_event)
                    .into(eventImage)
            } else {
                eventImage.setImageResource(R.drawable.placeholder_event)
            }
        }

    }
}