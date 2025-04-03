package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CraftFilterFragment : Fragment() {
    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    CraftFilterScreen(
                        filterState = craftViewModel.craftFilterState.collectAsState().value,
                        defaultFilter = craftViewModel.defaultFilterState,
                        countries = resources.getStringArray(R.array.country_list).toList(),
                        onApply = { newFilter ->
                            craftViewModel.updateFilter(newFilter)
                            findNavController().popBackStack()
                        },
                        onReset = {
                            craftViewModel.resetFilter()
                        }
                    )
                }
            }
        }
    }
}
