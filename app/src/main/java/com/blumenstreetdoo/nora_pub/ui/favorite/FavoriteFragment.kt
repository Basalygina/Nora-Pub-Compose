package com.blumenstreetdoo.nora_pub.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.FragmentFavoriteBinding
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by activityViewModel<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavBeerAdapter { onFavBeerClick(it) }
        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            favoriteViewModel.favoritesScreenState.collectLatest { state ->
                renderState(state)
            }
        }
    }

    private fun onFavBeerClick(favBeer: FavoriteBeer) {
        val action =
            FavoriteFragmentDirections.actionNavigationFavoriteToBeerDetailsFragment(favBeer.id)
        findNavController().navigate(action)

    }

    private fun renderState(state: FavoriteScreenState) {
        with(binding) {
            when (state) {
                is FavoriteScreenState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                    icError.visibility = View.GONE
                    messageError.visibility = View.GONE
                }

                is FavoriteScreenState.Content -> {
                    progressBar.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                    icError.visibility = View.GONE
                    messageError.visibility = View.GONE
                    (recycler.adapter as? FavBeerAdapter)?.updateBeerList(state.favorites)
                }

                is FavoriteScreenState.Error -> {
                    progressBar.visibility = View.GONE
                    recycler.visibility = View.GONE
                    icError.visibility = View.VISIBLE
                    messageError.visibility = View.VISIBLE
                    messageError.text = state.message
                }

                is FavoriteScreenState.Empty -> {
                    progressBar.visibility = View.GONE
                    recycler.visibility = View.GONE
                    icError.visibility = View.VISIBLE
                    messageError.visibility = View.VISIBLE
                    messageError.text = getString(R.string.no_favorites_message)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
