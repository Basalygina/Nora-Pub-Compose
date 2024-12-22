package com.blumenstreetdoo.nora_pub.ui.craft.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.DetailsBeerBinding
import com.bumptech.glide.Glide

class BeerDetailsFragment : Fragment() {
    private val args: BeerDetailsFragmentArgs by navArgs()
    private var _binding: DetailsBeerBinding? = null
    private val binding get() = _binding!!

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
        val currentBeer = args.beer
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
        }
    }
}