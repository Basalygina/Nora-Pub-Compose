package com.blumenstreetdoo.nora_pub.ui.craft

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blumenstreetdoo.nora_pub.databinding.ItemBeerBinding
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType


class BeerAdapter(
    private val beerList: List<Beer>,
    private val clickListener: BeerClickListener
) : RecyclerView.Adapter<BeerViewHolder>() {
    private var currentList: List<Beer> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding = ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerViewHolder(binding.root)
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val currentBeer = currentList[position]
        holder.bind(currentBeer)
        holder.itemView.setOnClickListener { clickListener.onBeerClick(currentBeer) }
    }

    fun updateBeerList(fullBeerList: List<Beer>, type: DrinkType) {
        currentList = fullBeerList.filter { it.type == type }
        notifyDataSetChanged()
    }

    fun interface BeerClickListener {
        fun onBeerClick(beer: Beer)
    }
}
