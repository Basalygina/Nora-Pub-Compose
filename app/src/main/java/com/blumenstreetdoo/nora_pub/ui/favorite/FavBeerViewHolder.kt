package com.blumenstreetdoo.nora_pub.ui.favorite

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.ItemBeerFavoriteBinding
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class FavBeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemBeerFavoriteBinding.bind(itemView)
    private val context = itemView.context

    fun bind(favBeer: FavoriteBeer) {
        with(binding) {

            beerName.text = favBeer.name
            if (favBeer.name.length + favBeer.brewery.name.length > 30) {
                brewerySecondLine.visibility = View.VISIBLE
                brewerySingleLine.visibility = View.INVISIBLE
                brewerySecondLine.text = context.getString(R.string.by_brewery, favBeer.brewery.name)
            } else {
                brewerySecondLine.visibility = View.GONE
                brewerySingleLine.visibility = View.VISIBLE
                brewerySingleLine.text = context.getString(R.string.by_brewery, favBeer.brewery.name)
            }
            if (favBeer.beerStyle != null) {
                beerStyle.visibility = View.VISIBLE
                beerStyle.text = favBeer.beerStyle
            } else {
                beerStyle.visibility = View.GONE
            }
            beerAbv.text = context.getString(R.string.ABV, favBeer.abv)
            if (favBeer.beerIbu != null) {
                beerIbu.visibility = View.VISIBLE
                beerIbu.text = context.getString(R.string.IBU, favBeer.beerIbu)
            } else {
                beerIbu.visibility = View.GONE
            }
            if (favBeer.imageUrl != null) {
                Glide.with(beerImage.context)
                    .load(favBeer.imageUrl)
                    .placeholder(R.drawable.placeholder_nora_large)
                    .transform(RoundedCorners(8))
                    .into(beerImage)
            } else {
                beerImage.setImageResource(R.drawable.placeholder_nora_large)
            }
        }
    }
}
