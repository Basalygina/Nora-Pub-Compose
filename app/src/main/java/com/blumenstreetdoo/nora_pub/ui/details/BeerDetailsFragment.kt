package com.blumenstreetdoo.nora_pub.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.data.toFavoriteBeer
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.toDetails
import com.blumenstreetdoo.nora_pub.ui.craft.CraftViewModel
import com.blumenstreetdoo.nora_pub.ui.favorite.FavoriteViewModel
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
        return inflater.inflate(R.layout.details_beer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            MaterialTheme {
                val scope = rememberCoroutineScope()
                val beerId = args.beerId
                val beer by craftViewModel
                    .getBeerById(beerId)
                    .collectAsState(null)
                val favoriteBeer by favoriteViewModel
                    .getFavoriteBeerById(beerId)
                    .collectAsState(null)

                var beerDetails by remember { mutableStateOf<BeerDetails?>(null) }

                LaunchedEffect(beer, favoriteBeer) {
                    if (favoriteBeer != null) {
                        beerDetails = favoriteBeer!!.toDetails()
                    } else if (beer != null) {
                        beerDetails = beer!!.toDetails(false)
                    }
                }

                if (beerDetails != null) {
                    BeerDetailsScreen(
                        beerDetails = beerDetails!!,
                        onToggleFavorite = {
                            scope.launch {
                                if (favoriteBeer != null) {
                                    // Remove from favorite
                                    favoriteViewModel.toggleFavorite(favoriteBeer!!)
                                } else {
                                    // Add to favorite
                                    if (beer != null) {
                                        favoriteViewModel.toggleFavorite(beer!!.toFavoriteBeer())
                                    }
                                }
                            }
                        },
                        onShareClick = { shareBeerDetails(it) },
                        onNoteChange = { note ->
                            scope.launch {
                                favoriteViewModel.updateFavoriteNote(beerId, note)
                                beerDetails = beerDetails!!.copy(note = note)
                            }
                        },
                        onBackClick = { findNavController().popBackStack() }
                    )
                } else {
                    Text(getString(R.string.loading))
                }
            }
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


//private fun toggleFavorite() {
//    viewLifecycleOwner.lifecycleScope.launch {
//        if (favoriteBeer != null) {
//            // Удаляем из избранного
//            favoriteViewModel.toggleFavorite(favoriteBeer!!)
//            val originalBeer = craftViewModel.getBeerById(favoriteBeer!!.id)
//            favoriteBeer = null
//            currentBeer = originalBeer
//
//            binding.noteInputLayout.visibility = View.GONE
//        } else {
//            // Добавляем в избранное
//            val newFavorite = currentBeer?.toFavoriteBeer()
//            favoriteViewModel.toggleFavorite(newFavorite!!)
//            favoriteBeer = newFavorite
//            currentBeer = null
//
//            binding.noteInputLayout.visibility = View.VISIBLE
//            binding.noteEditText.setText("")
//        }
//        updateFavoriteIcon(favoriteBeer != null)
//
//        if (favoriteBeer == null && currentBeer == null) {
//            Snackbar.make(binding.root, getString(R.string.beer_removed), Snackbar.LENGTH_SHORT)
//                .show()
//            findNavController().popBackStack()
//        }
//    }
//}

//@SuppressLint("RestrictedApi")
//private fun setupUIForFavorite(favBeer: FavoriteBeer) {
//    with(binding) {
//        name.text = favBeer.name
//        beerDescription.text = favBeer.description
//        beerAbv.text = getString(R.string.ABV, favBeer.abv)
//        beerIbu.text = favBeer.beerIbu?.let { getString(R.string.IBU, it) }
//        noteInputLayout.visibility = View.VISIBLE
//        noteEditText.setText(favBeer.note)
//        noteInputLayout.hint = if (noteEditText.text.isNullOrEmpty()) {
//            getString(R.string.add_note_hint)
//        } else {
//            getString(R.string.edit_note_hint)
//
//        }
//
//        noteEditText.doOnTextChanged { text, _, _, _ ->
//            icDone.visibility = View.VISIBLE
//
//            noteInputLayout.hint = if (text.isNullOrEmpty()) {
//                getString(R.string.add_note_hint)
//            } else {
//                getString(R.string.edit_note_hint)
//
//            }
//            icDone.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
//            favoriteViewModel.updateFavoriteNote(favBeer.id, text.toString())
//        }
//
//        noteEditText.setOnFocusChangeListener { _, hasFocus ->
//            icDone.visibility =
//                if (hasFocus && !noteEditText.text.isNullOrEmpty()) View.VISIBLE else View.GONE
//        }
//
//        icDone.setOnClickListener {
//            noteEditText.clearFocus()
//            hideKeyboard(noteEditText)
//            icDone.visibility = View.GONE
//        }
//
//    }
//    setupBeerImage(favBeer.imageUrl)
//    setupBeerStyle(favBeer.beerStyle)
//    setupBreweryText(
//        beerName = favBeer.name,
//        breweryName = favBeer.brewery.name
//    )
//}

