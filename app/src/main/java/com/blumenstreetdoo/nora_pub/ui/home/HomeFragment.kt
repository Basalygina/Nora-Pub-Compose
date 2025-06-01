package com.blumenstreetdoo.nora_pub.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.ui.reservation.ReservationBottomSheet
import com.blumenstreetdoo.nora_pub.ui.reservation.ReservationViewModel
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val reservationViewModel: ReservationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme(
                colorScheme = NoraColors,
                typography = NoraTypography
            ) {
                val state by homeViewModel.homeScreenState.collectAsState()

                HomeScreen(
                    state = state,
                    onEventClick = { event ->
                        val action = HomeFragmentDirections
                            .actionNavigationHomeToEventDetailsFragment(event)
                        findNavController().navigate(action)
                    },
                    onNewsClick = { news ->
                        val action = HomeFragmentDirections
                            .actionNavigationHomeToNewsDetailsFragment(news)
                        findNavController().navigate(action)
                    },
                    onMenuClick = {
                        val action = HomeFragmentDirections.actionNavigationHomeToMenuFragment()
                        findNavController().navigate(action)
                    },
                    onUntappdClick = {
                        val url = getString(R.string.url_nora_on_untappd)
                        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                    },
                    onPhoneClick = { phone ->
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:$phone".toUri()
                        }
                        startActivity(intent)
                    },
                    onDirectionsClick = {
                        val url = getString(R.string.url_nora_on_google_maps)
                        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                    },
                    onInstagramClick = {
                        val url = getString(R.string.url_nora_on_instagram)
                        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                    },
                    onBookATableClick = {
                        val bottomSheet = BottomSheetDialog(requireContext())
                        val composeView = ComposeView(requireContext()).apply {
                            setContent {
                                MaterialTheme(
                                    colorScheme = NoraColors,
                                    typography = NoraTypography
                                ) {
                                    ReservationBottomSheet(
                                        viewModel = reservationViewModel,
                                        onDismiss = { bottomSheet.dismiss() }
                                    )
                                }
                            }
                        }
                        bottomSheet.setContentView(composeView)
                        bottomSheet.show()
                    },
                    onSeeAllNewsClick = { }
                )
            }
        }
    }
}