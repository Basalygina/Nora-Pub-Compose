package com.blumenstreetdoo.nora_pub.ui.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.DetailsNewsBinding
import com.blumenstreetdoo.nora_pub.domain.models.NewsType
import com.bumptech.glide.Glide

class NewsDetailsFragment : Fragment() {
    private val args: NewsDetailsFragmentArgs by navArgs()
    private var _binding: DetailsNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentNews = args.news
        with(binding) {
            newsType.visibility =
                if (currentNews.type == NewsType.NEW_ARRIVAL) View.VISIBLE else View.GONE
            newsTitle.text = currentNews.title
            newsDescription.text = currentNews.description
            if (currentNews.imageUrl != null) {
                Glide.with(newsImage.context)
                    .load(currentNews.imageUrl)
                    .placeholder(R.drawable.placeholder_nora)
                    .into(newsImage)
            } else {
                newsImage.setImageResource(R.drawable.placeholder_nora)
            }
        }

    }
}