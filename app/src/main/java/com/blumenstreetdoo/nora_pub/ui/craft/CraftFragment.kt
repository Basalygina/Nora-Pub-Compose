package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CraftFragment : Fragment() {
    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_craft, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            MaterialTheme(
                colorScheme = NoraColors,
                typography = NoraTypography,
            ) {
                CraftScreen(
                    craftViewModel = craftViewModel,
                    onBeerClick = { beerDetails -> onBeerClick(beerDetails) },
                )
            }
        }
    }

    private fun onBeerClick(beerDetails: BeerDetails) {
        val action =
            CraftFragmentDirections.actionNavigationCraftToBeerDetailsFragment(beerDetails.id)
        findNavController().navigate(action)
    }
}
