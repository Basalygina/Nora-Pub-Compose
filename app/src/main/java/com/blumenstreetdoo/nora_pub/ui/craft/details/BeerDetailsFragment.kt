package com.blumenstreetdoo.nora_pub.ui.craft.details

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.DetailsBeerBinding
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.ui.favorite.FavoriteViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class BeerDetailsFragment : Fragment() {
    private val args: BeerDetailsFragmentArgs by navArgs()
    private var _binding: DetailsBeerBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by activityViewModel<FavoriteViewModel>()
    private val currentBeer: Beer by lazy { args.beer }
    private var favoriteItem: MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteViewModel.checkFavorite(currentBeer.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupBeerDetails()
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
                        favoriteViewModel.toggleFavorite(currentBeer)
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

    private fun setupBeerDetails() {
        with(binding) {
            name.text = currentBeer.name
            beerDescription.text = currentBeer.description

            setupBreweryText()
            setupBeerStyle()
            setupBeerDetailsValues()
            setupBeerImage()
        }
    }

    private fun setupBreweryText() {
        with(binding) {
            if (currentBeer.name.length + currentBeer.brewery.name.length > 40) {
                brewerySecondLine.visibility = View.VISIBLE
                brewerySingleLine.visibility = View.INVISIBLE
                brewerySecondLine.text = getString(R.string.by_brewery, currentBeer.brewery.name)
            } else {
                brewerySecondLine.visibility = View.GONE
                brewerySingleLine.visibility = View.VISIBLE
                brewerySingleLine.text = getString(R.string.by_brewery, currentBeer.brewery.name)
            }
        }
    }

    private fun setupBeerStyle() {
        with(binding) {
            if (currentBeer.beerStyle != null) {
                style.visibility = View.VISIBLE
                style.text = currentBeer.beerStyle
            } else {
                style.visibility = View.GONE
            }
        }
    }

    private fun setupBeerDetailsValues() {
        with(binding) {
            beerAbv.text = getString(R.string.ABV, currentBeer.abv)

            if (currentBeer.beerIbu != null) {
                beerIbu.visibility = View.VISIBLE
                beerIbu.text = getString(R.string.IBU, currentBeer.beerIbu)
            } else {
                beerIbu.visibility = View.GONE
            }
        }
    }

    private fun setupBeerImage() {
        with(binding) {
            if (currentBeer.imageUrl != null) {
                Glide.with(image.context)
                    .load(currentBeer.imageUrl)
                    .placeholder(R.drawable.placeholder_nora)
                    .into(image)
            } else {
                image.setImageResource(R.drawable.placeholder_nora)
            }
        }
    }

    private fun shareBeerDetails() {
        val shareText = buildString {
            append("${currentBeer.name} — ${currentBeer.brewery.name}\n")
            append(getString(R.string.ABV, currentBeer.abv))
            currentBeer.beerIbu?.let { append("\n${getString(R.string.IBU, it)}") }
            append("\n\n${currentBeer.description}")
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
