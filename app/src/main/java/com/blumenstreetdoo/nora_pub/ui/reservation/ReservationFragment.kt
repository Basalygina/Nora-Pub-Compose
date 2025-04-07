package com.blumenstreetdoo.nora_pub.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReservationFragment : Fragment() {
    private val viewModel: ReservationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme(
                    colorScheme = NoraColors,
                    typography = NoraTypography,
                ) {
                    ReservationScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}