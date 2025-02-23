package com.blumenstreetdoo.nora_pub.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.data.toFavoriteBeer
import com.blumenstreetdoo.nora_pub.databinding.DetailsBeerBinding
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import com.blumenstreetdoo.nora_pub.ui.craft.CraftViewModel
import com.blumenstreetdoo.nora_pub.ui.favorite.FavoriteViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class BeerDetailsFragment : Fragment() {
    private val args: BeerDetailsFragmentArgs by navArgs()
    private var _binding: DetailsBeerBinding? = null
    private val binding get() = _binding!!

    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()
    private val favoriteViewModel: FavoriteViewModel by activityViewModel<FavoriteViewModel>()

    private var currentBeer: Beer? = null
    private var favoriteBeer: FavoriteBeer? = null
    private var favoriteItem: MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val beerId = args.beerId
        viewLifecycleOwner.lifecycleScope.launch {
            loadBeerDetails(beerId)
        }
        setupMenu()
    }

    private suspend fun loadBeerDetails(beerId: String) {
        favoriteBeer = favoriteViewModel.getFavoriteBeerById(beerId)

        if (favoriteBeer != null) {
            setupUIForFavorite(favoriteBeer!!)
            return
        } else {
            currentBeer = craftViewModel.getBeerById(beerId)
            setupUIForRegularBeer(currentBeer!!)
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_main, menu)

                favoriteItem = menu.findItem(R.id.action_favorite)
                favoriteItem?.isVisible = true

                viewLifecycleOwner.lifecycleScope.launch {
                    favoriteViewModel.isFavorite.collect { isFav ->
                        updateFavoriteIcon(isFav)
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_favorite -> {
                        toggleFavorite()
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

    private fun toggleFavorite() {
        viewLifecycleOwner.lifecycleScope.launch {
            if (favoriteBeer != null) {
                // Удаляем из избранного
                favoriteViewModel.toggleFavorite(favoriteBeer!!)
                val originalBeer = craftViewModel.getBeerById(favoriteBeer!!.id)
                favoriteBeer = null
                currentBeer = originalBeer

                binding.noteInputLayout.visibility = View.GONE
            } else {
                // Добавляем в избранное
                val newFavorite = currentBeer?.toFavoriteBeer()
                favoriteViewModel.toggleFavorite(newFavorite!!)
                favoriteBeer = newFavorite
                currentBeer = null

                binding.noteInputLayout.visibility = View.VISIBLE
                binding.noteEditText.setText("")
            }
            updateFavoriteIcon(favoriteBeer != null) // Обновляем иконку

            if (favoriteBeer == null && currentBeer == null) {
                Snackbar.make(binding.root, getString(R.string.beer_removed), Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun setupUIForRegularBeer(beer: Beer) {
        with(binding) {
            name.text = beer.name
            beerDescription.text = beer.description
            beerAbv.text = getString(R.string.ABV, beer.abv)
            beerIbu.text = beer.beerIbu?.let { getString(R.string.IBU, it) }
            noteInputLayout.visibility = View.GONE
        }
        setupBeerImage(beer.imageUrl)
        setupBeerStyle(beer.beerStyle)
        setupBreweryText(
            beerName = beer.name,
            breweryName = beer.brewery.name
        )
    }

    private fun setupUIForFavorite(favBeer: FavoriteBeer) {
        with(binding) {
            name.text = favBeer.name
            beerDescription.text = favBeer.description
            beerAbv.text = getString(R.string.ABV, favBeer.abv)
            beerIbu.text = favBeer.beerIbu?.let { getString(R.string.IBU, it) }
            noteInputLayout.visibility = View.VISIBLE
            noteEditText.setText(favBeer.note)

            noteEditText.doOnTextChanged { text, _, _, _ ->
                binding.noteInputLayout.hint = if (text.isNullOrEmpty()) {
                    getString(R.string.add_note_hint)
                } else {
                    getString(R.string.edit_note_hint)
                }
                favoriteViewModel.updateFavoriteNote(favBeer.id, text.toString())
            }
        }
        setupBeerImage(favBeer.imageUrl)
        setupBeerStyle(favBeer.beerStyle)
        setupBreweryText(
            beerName = favBeer.name,
            breweryName = favBeer.brewery.name
        )
    }

    private fun setupBreweryText(beerName: String, breweryName: String) {
        with(binding) {
            if (beerName.length + breweryName.length > 40) {
                brewerySecondLine.visibility = View.VISIBLE
                brewerySingleLine.visibility = View.INVISIBLE
                brewerySecondLine.text = getString(R.string.by_brewery, breweryName)
            } else {
                brewerySecondLine.visibility = View.GONE
                brewerySingleLine.visibility = View.VISIBLE
                brewerySingleLine.text = getString(R.string.by_brewery, breweryName)
            }
        }
    }

    private fun setupBeerStyle(beerStyle: String?) {
        with(binding) {
            if (beerStyle != null) {
                style.visibility = View.VISIBLE
                style.text = beerStyle
            } else {
                style.visibility = View.GONE
            }
        }
    }

    private fun setupBeerImage(imageUrl: String?) {
        with(binding) {
            if (imageUrl != null) {
                Glide.with(image.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_nora)
                    .into(image)
            } else {
                image.setImageResource(R.drawable.placeholder_nora)
            }
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        favoriteItem?.setIcon(
            if (isFavorite) R.drawable.ic_favorites_active_red
            else R.drawable.ic_favorites_not_active
        )
    }

    private fun shareBeerDetails() {
        val shareText = buildString {
            favoriteBeer?.let {
                append("${it.name} — ${it.brewery.name}\n")
                append(getString(R.string.ABV, it.abv))
                it.beerIbu?.let { ibu -> append("\n${getString(R.string.IBU, ibu)}") }
                append("\n\n${it.description}")
            }
            currentBeer?.let {
                append("${it.name} — ${it.brewery.name}\n")
                append(getString(R.string.ABV, it.abv))
                it.beerIbu?.let { ibu -> append("\n${getString(R.string.IBU, ibu)}") }
                append("\n\n${it.description}")
            }
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
