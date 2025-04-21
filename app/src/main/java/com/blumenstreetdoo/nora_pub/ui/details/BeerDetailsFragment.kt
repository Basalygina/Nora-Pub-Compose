package com.blumenstreetdoo.nora_pub.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.data.toFavoriteBeer
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.toDetails
import com.blumenstreetdoo.nora_pub.ui.common.ErrorState
import com.blumenstreetdoo.nora_pub.ui.common.LoadingState
import com.blumenstreetdoo.nora_pub.ui.craft.CraftViewModel
import com.blumenstreetdoo.nora_pub.ui.profile.FavoriteViewModel
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class BeerDetailsFragment : Fragment() {
    private val args: BeerDetailsFragmentArgs by navArgs()
    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()
    private val favoriteViewModel: FavoriteViewModel by activityViewModel<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme(
                    colorScheme = NoraColors,
                    typography = NoraTypography,
                ) {
                    BeerDetailsContent()
                }
            }
        }
    }

    @SuppressLint("FlowOperatorInvokedInComposition")
    @Composable
    private fun BeerDetailsContent() {
        val scope = rememberCoroutineScope()
        val beerId = args.beerId

        val beerFlow = craftViewModel.getBeerById(beerId).flowWithLifecycle(lifecycle)
        val favBeerFlow = favoriteViewModel.getFavoriteBeerById(beerId).flowWithLifecycle(lifecycle)
        var errorState by rememberSaveable { mutableStateOf<String?>(null) }

        // Combine beer and favorite flows with error handling
        val beerDetails by beerFlow.combine(favBeerFlow) { beer, favorite ->
            when {
                favorite != null -> favorite.toDetails() // Use favorite beer if available
                beer != null -> beer.toDetails(isFavorite = false) // Otherwise, use regular beer
                else -> null
            }
        }.catch { errorState = getString(R.string.error_with_beer_details) }
            .collectAsStateWithLifecycle(initialValue = null)

        when {
            errorState != null -> ErrorState(errorState!!)
            beerDetails != null -> {
                BeerDetailsScreen(
                    beerDetails = beerDetails!!,
                    onToggleFavorite = {
                        scope.launch {
                            beerDetails?.let {
                                if (it.isFavorite) {
                                    favoriteViewModel.removeFavorite(it.id)
                                } else {
                                    craftViewModel.getBeerById(it.id)
                                        .first()?.let { beer ->
                                            favoriteViewModel.addFavorite(beer.toFavoriteBeer())
                                        }
                                }
                            }
                        }
                    },
                    onShareClick = { shareBeerDetails(it) },
                    onNoteChange = { note ->
                        scope.launch {
                            beerDetails?.id?.let { id ->
                                favoriteViewModel.updateFavoriteNote(id, note)
                            }
                        }
                    },
                    onBackClick = { findNavController().popBackStack() }
                )
            }
            else -> LoadingState()
        }
    }

    private fun shareBeerDetails(beer: BeerDetails) {
        val shareText = buildString {
            append("${beer.name} — ${beer.brewery.name}\n")
            append(getString(R.string.ABV, beer.abv))
            beer.beerIbu?.let { ibu -> append("\n${getString(R.string.IBU, ibu)}") }
            append("\n\n${beer.description}")
        }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share)))
    }
}