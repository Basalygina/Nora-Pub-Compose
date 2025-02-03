package com.blumenstreetdoo.nora_pub.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.databinding.ItemBeerFavoriteBinding
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer


class FavBeerAdapter(
    private val clickListener: FavBeerClickListener
) : RecyclerView.Adapter<FavBeerViewHolder>() {
    private var currentList: MutableList<FavoriteBeer> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavBeerViewHolder {
        val binding = ItemBeerFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavBeerViewHolder(binding.root)
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: FavBeerViewHolder, position: Int) {
        val currentBeer = currentList[position]
        holder.bind(currentBeer)
        holder.itemView.setOnClickListener { clickListener.onBeerClick(currentBeer) }
    }

    fun updateBeerList(newBeerList: List<FavoriteBeer>) {
        currentList.clear()
        currentList.addAll(newBeerList)
        notifyDataSetChanged()
    }

    fun interface FavBeerClickListener {
        fun onBeerClick(favBeer: FavoriteBeer)
    }
}
