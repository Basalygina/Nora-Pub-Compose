package com.blumenstreetdoo.nora_pub.ui.craft.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.DetailsBeerBinding
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.ui.favorite.FavoriteViewModel
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class BeerDetailsFragment : Fragment() {
    private val args: BeerDetailsFragmentArgs by navArgs()
    private var _binding: DetailsBeerBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by activityViewModel<FavoriteViewModel>()
    private val currentBeer: Beer by lazy { args.beer }

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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_main, menu)

                val favoriteItem = menu.findItem(R.id.action_favorite)
                favoriteViewModel.isFavorite.observe(viewLifecycleOwner) { isFav ->
                    favoriteItem.setIcon(
                        if (isFav) R.drawable.ic_favorites_active_red
                        else R.drawable.ic_favorites_not_active
                    )
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_favorite -> {
                        favoriteViewModel.toggleFavorite(currentBeer)
                        requireActivity().invalidateOptionsMenu()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        with(binding) {

            name.text = currentBeer.name
            beerDescription.text = currentBeer.description

            if (currentBeer.name.length + currentBeer.brewery.name.length > 40) {
                brewerySecondLine.visibility = View.VISIBLE
                brewerySingleLine.visibility = View.INVISIBLE
                brewerySecondLine.text = getString(R.string.by_brewery, currentBeer.brewery.name)
            } else {
                brewerySecondLine.visibility = View.GONE
                brewerySingleLine.visibility = View.VISIBLE
                brewerySingleLine.text = getString(R.string.by_brewery, currentBeer.brewery.name)
            }
            if (currentBeer.beerStyle != null) {
                style.visibility = View.VISIBLE
                style.text = currentBeer.beerStyle
            } else {
                style.visibility = View.GONE
            }
            beerAbv.text = getString(R.string.ABV, currentBeer.abv)
            if (currentBeer.beerIbu != null) {
                beerIbu.visibility = View.VISIBLE
                beerIbu.text = getString(R.string.IBU, currentBeer.beerIbu)
            } else {
                beerIbu.visibility = View.GONE
            }

            if (currentBeer.imageUrl != null) {
                Glide.with(image.context)
                    .load(currentBeer.imageUrl)
                    .placeholder(R.drawable.placeholder_nora)
                    .into(image)
            } else {
                image.setImageResource(R.drawable.placeholder_nora)
            }
            favoriteViewModel.checkFavorite(currentBeer.id)

            favoriteViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
                Log.d("DTest", "Favorite state changed: $isFavorite")
            }

        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().invalidateOptionsMenu()
    }

    override fun onPause() {
        super.onPause()
        requireActivity().invalidateOptionsMenu()
    }
}