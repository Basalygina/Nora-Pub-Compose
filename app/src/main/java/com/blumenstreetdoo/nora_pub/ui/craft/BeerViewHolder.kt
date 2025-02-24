package com.blumenstreetdoo.nora_pub.ui.craft

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.ItemBeerBinding
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class BeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemBeerBinding.bind(itemView)
    private val context = itemView.context

    fun bind(beer: Beer) {
        with(binding) {
            price.text = if (beer.price % 1 == 0.0) {
                "${beer.price.toInt()}€"
            } else {
                "${beer.price}€"
            }
            volume.text = context.getString(R.string.volume, beer.volume)
            beerName.text = beer.name
            if (beer.name.length + beer.brewery.name.length > 20) {
                brewerySecondLine.visibility = View.VISIBLE
                brewerySingleLine.visibility = View.INVISIBLE
                brewerySecondLine.text = context.getString(R.string.by_brewery, beer.brewery.name)
            } else {
                brewerySecondLine.visibility = View.GONE
                brewerySingleLine.visibility = View.VISIBLE
                brewerySingleLine.text = context.getString(R.string.by_brewery, beer.brewery.name)
            }
            if (beer.beerStyle != null) {
                beerStyle.visibility = View.VISIBLE
                beerStyle.text = beer.beerStyle
            } else {
                beerStyle.visibility = View.GONE
            }
            beerAbv.text = context.getString(R.string.ABV, beer.abv)
            if (beer.beerIbu != null) {
                beerIbu.visibility = View.VISIBLE
                beerIbu.text = context.getString(R.string.IBU, beer.beerIbu)
            } else {
                beerIbu.visibility = View.GONE
            }
            if (beer.imageUrl != null) {
                Glide.with(beerImage.context)
                    .load(beer.imageUrl)
                    .transform(RoundedCorners(8))
                    .placeholder(R.drawable.placeholder_nora_large)
                    .into(beerImage)
            } else {
                beerImage.setImageResource(R.drawable.placeholder_nora_large)
            }
        }
    }
}
