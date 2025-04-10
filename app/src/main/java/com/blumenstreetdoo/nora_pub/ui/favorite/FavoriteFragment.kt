package com.blumenstreetdoo.nora_pub.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoriteFragment : Fragment() {
    private val favoriteViewModel: FavoriteViewModel by activityViewModel<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            MaterialTheme(
                colorScheme = NoraColors,
                typography = NoraTypography,
            ) {
                val state by favoriteViewModel.favoritesScreenState.collectAsState()
                FavoritesScreen(
                    state = state,
                    onItemClick = { beerDetails -> onFavBeerClick(beerDetails) },
                    onIconFavoriteClick = { favBeer -> onIconFavoriteClick(favBeer) }
                )
            }
        }
    }

    private fun onFavBeerClick(beerDetails: BeerDetails) {
        val action =
            FavoriteFragmentDirections.actionNavigationFavoriteToBeerDetailsFragment(beerDetails.id)
        findNavController().navigate(action)
    }

    private fun onIconFavoriteClick(favBeer: FavoriteBeer) {
        favoriteViewModel.toggleFavorite(favBeer)
    }

}
