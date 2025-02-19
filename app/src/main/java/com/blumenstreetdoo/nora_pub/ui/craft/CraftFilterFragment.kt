package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.FragmentFilterBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.NumberFormat

class CraftFilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()
    private val args: CraftFilterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentFilter = args.filterState ?: CraftFilterState()
        val countries = resources.getStringArray(R.array.country_list).toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, countries)
        with(binding) {
            dropdownCountry.setAdapter(adapter)
            dropdownCountry.setText(currentFilter.country, false)

            sliderAbv.setLabelFormatter { value: Float ->
                val format = NumberFormat.getPercentInstance()
                format.maximumFractionDigits = 1
                format.format(value.toDouble()/100)
            }
            sliderAbv.setValues(
                currentFilter.minAbv?.toFloat() ?: 0f,
                currentFilter.maxAbv?.toFloat() ?: 16f
            )

            sliderIbu.setLabelFormatter { value: Float ->
                val format = NumberFormat.getIntegerInstance()
                format.maximumFractionDigits = 0
                format.format(value.toDouble())
            }
            sliderIbu.setValues(
                currentFilter.minIbu?.toFloat() ?: 0f,
                currentFilter.maxIbu?.toFloat() ?: 100f
            )

            btnApplyFilter.setOnClickListener {
                val selectedCountry = binding.dropdownCountry.text.toString()
                val abvValues = binding.sliderAbv.values
                val minAbv = abvValues.firstOrNull()?.toDouble()
                val maxAbv = abvValues.lastOrNull()?.toDouble()

                val ibuValues = binding.sliderIbu.values
                val minIbu = ibuValues.firstOrNull()?.toDouble()
                val maxIbu = ibuValues.lastOrNull()?.toDouble()

                val newFilter = CraftFilterState(
                    searchQuery = craftViewModel.craftFilterState.value.searchQuery,
                    country = selectedCountry,
                    minAbv = minAbv,
                    maxAbv = maxAbv,
                    minIbu = minIbu,
                    maxIbu = maxIbu
                )

                craftViewModel.updateFilter(newFilter)
                findNavController().popBackStack()
            }
        }

    }
}