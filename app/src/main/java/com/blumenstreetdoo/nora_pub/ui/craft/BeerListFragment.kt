package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class BeerListFragment : Fragment() {
    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_beer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            MaterialTheme {
                val state by craftViewModel.craftState.collectAsState()
                BeerListScreen(
                    drinkType = DrinkType.TAP_BEER,
                    craftState = state,
                    onBeerClick = {beerDetails -> onBeerClick(beerDetails)}
                )
            }
        }
    }

    private fun onBeerClick(beerDetails: BeerDetails) {
        val action = CraftFragmentDirections.actionNavigationCraftToBeerDetailsFragment(beerDetails.id)
        findNavController().navigate(action)
    }

    companion object {
        fun newInstance(): Fragment = BeerListFragment()
    }
}