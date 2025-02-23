package com.blumenstreetdoo.nora_pub.ui.favorite.details

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.DetailsFavoriteBeerBinding
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import com.blumenstreetdoo.nora_pub.ui.favorite.FavoriteViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoriteBeerDetailsFragment : Fragment() {
    private val args: FavoriteBeerDetailsFragmentArgs by navArgs()
    private var _binding: DetailsFavoriteBeerBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by activityViewModel<FavoriteViewModel>()
    private val favoriteBeer: FavoriteBeer by lazy { args.favBeer }
    private var favoriteItem: MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFavoriteBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteViewModel.checkFavorite(favoriteBeer.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()
        setupBeerDetails()
        setupNoteInput()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_main, menu)

                favoriteItem = menu.findItem(R.id.action_favorite)
                val shareItem = menu.findItem(R.id.action_share)

                favoriteItem?.isVisible = true
                shareItem.isVisible = true

                updateFavoriteIcon(favoriteViewModel.isFavorite.value)

                viewLifecycleOwner.lifecycleScope.launch {
                    favoriteViewModel.isFavorite.collect { isFav ->
                        updateFavoriteIcon(isFav)
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_favorite -> {
                        favoriteViewModel.toggleFavorite(favoriteBeer)
                        true
                    }
                    R.id.action_share -> {
                        shareBeerDetails()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        favoriteItem?.setIcon(
            if (isFavorite) R.drawable.ic_favorites_active_red
            else R.drawable.ic_favorites_not_active
        )
    }

    private fun shareBeerDetails() {
        val shareText = buildString {
            append("${favoriteBeer.name} — ${favoriteBeer.brewery.name}\n")
            append(getString(R.string.ABV, favoriteBeer.abv))
            favoriteBeer.beerIbu?.let { append("\n${getString(R.string.IBU, it)}") }
            append("\n\n${favoriteBeer.description}")
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share)))
    }

    private fun setupBeerDetails() {
        with(binding) {
            name.text = favoriteBeer.name
            beerDescription.text = favoriteBeer.description

            setupBreweryText()
            setupBeerStyle()
            setupBeerDetailsValues()
            setupBeerImage()
        }
    }

    private fun setupBreweryText() {
        with(binding) {
            if (favoriteBeer.name.length + favoriteBeer.brewery.name.length > 40) {
                brewerySecondLine.visibility = View.VISIBLE
                brewerySingleLine.visibility = View.INVISIBLE
                brewerySecondLine.text = getString(R.string.by_brewery, favoriteBeer.brewery.name)
            } else {
                brewerySecondLine.visibility = View.GONE
                brewerySingleLine.visibility = View.VISIBLE
                brewerySingleLine.text = getString(R.string.by_brewery, favoriteBeer.brewery.name)
            }
        }
    }

    private fun setupBeerStyle() {
        with(binding) {
            if (favoriteBeer.beerStyle != null) {
                style.visibility = View.VISIBLE
                style.text = favoriteBeer.beerStyle
            } else {
                style.visibility = View.GONE
            }
        }
    }

    private fun setupBeerDetailsValues() {
        with(binding) {
            beerAbv.text = getString(R.string.ABV, favoriteBeer.abv)

            if (favoriteBeer.beerIbu != null) {
                beerIbu.visibility = View.VISIBLE
                beerIbu.text = getString(R.string.IBU, favoriteBeer.beerIbu)
            } else {
                beerIbu.visibility = View.GONE
            }
        }
    }


    private fun setupNoteInput() {
        with(binding){
            noteEditText.setText(favoriteBeer.note)
            noteEditText.doOnTextChanged { text, _, _, _ ->
                binding.noteInputLayout.hint = if (text.isNullOrEmpty()) {
                    getString(R.string.add_note_hint) // Если поле пустое
                } else {
                    getString(R.string.edit_note_hint) // Если есть текст
                }
                saveNote(text.toString())
            }
        }
    }
    private fun setupBeerImage() {
        with(binding) {
            if (favoriteBeer.imageUrl != null) {
                Glide.with(image.context)
                    .load(favoriteBeer.imageUrl)
                    .placeholder(R.drawable.placeholder_nora)
                    .into(image)
            } else {
                image.setImageResource(R.drawable.placeholder_nora)
            }
        }
    }


    private fun saveNote(note: String) {
        lifecycleScope.launch {
            favoriteViewModel.updateFavoriteNote(favoriteBeer.id, note)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
