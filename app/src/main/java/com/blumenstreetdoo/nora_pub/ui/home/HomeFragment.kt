package com.blumenstreetdoo.nora_pub.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.FragmentHomeBinding
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val eventsAdapter by lazy { EventsAdapter(mutableListOf()) { onEventClick(it) } }
    private val newsAdapter by lazy { NewsAdapter(mutableListOf()) { onNewsClick(it) } }
    private val homeViewModel: HomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("NTest", "HomeFragment onCreateView")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            homeViewModel.homeScreenState.collect { state ->
                when (state) {
                    HomeScreenState.Loading -> showLoading()
                    is HomeScreenState.Content -> showContent(state.events, state.news)
                    is HomeScreenState.Error -> showError(noInternet = false)
                    HomeScreenState.NoInternet -> showError(noInternet = true)
                }
            }
        }

        with(binding) {
            viewPagerEvents.adapter = eventsAdapter
            rvNews.adapter = newsAdapter
            viewPagerEvents.setPageTransformer { page, position ->
                val scaleFactor = if (position == 0f) {
                    1f // Первый элемент на полную ширину
                } else {
                    0.6f + (1 - Math.abs(position)) * 0.4f // Все остальные элементы меньше
                }
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
              //  val scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f
              //  page.scaleX = scaleFactor
              //  page.scaleY = scaleFactor
            }
            TabLayoutMediator(tabDots, viewPagerEvents) { _, _ -> }.attach()

            buttonAbout.setOnClickListener {
                val scrollViewHeight = scrollView.getChildAt(0).height
                scrollView.smoothScrollTo(0, scrollViewHeight)
            }

            buttonUntappd.setOnClickListener {
                val url = getString(R.string.nora_on_untappd) // Получаем ссылку из ресурсов
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            overlay.visibility = View.GONE
            errorView.visibility = View.GONE
            viewPagerEvents.visibility = View.INVISIBLE
            rvNews.visibility = View.INVISIBLE
            eventsProgressBar.visibility = View.VISIBLE
            newsProgressBar.visibility = View.VISIBLE
        }
    }

    private fun showContent(events: List<Event>, news: List<News>) {
        with(binding) {
            overlay.visibility = View.GONE
            errorView.visibility = View.GONE
            viewPagerEvents.visibility = View.VISIBLE
            rvNews.visibility = View.VISIBLE
            eventsProgressBar.visibility = View.GONE
            newsProgressBar.visibility = View.GONE
        }
        eventsAdapter.updateEvents(events)
        newsAdapter.updateNews(news)
    }

    private fun showError(noInternet: Boolean) {
        with(binding) {
            if (noInternet) messageError.text =
                getString(R.string.error_message_no_internet)
            else messageError.text =
                getString(R.string.error_message)
            overlay.visibility = View.VISIBLE
            errorView.visibility = View.VISIBLE
            viewPagerEvents.visibility = View.INVISIBLE
            rvNews.visibility = View.INVISIBLE
            eventsProgressBar.visibility = View.GONE
            newsProgressBar.visibility = View.GONE
        }
    }

    private fun onNewsClick(news: News) {
        val action = HomeFragmentDirections.actionNavigationHomeToNewsDetailsFragment(news)
        findNavController().navigate(action)
    }

    private fun onEventClick(event: Event) {
        val action = HomeFragmentDirections.actionNavigationHomeToEventDetailsFragment(event)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}