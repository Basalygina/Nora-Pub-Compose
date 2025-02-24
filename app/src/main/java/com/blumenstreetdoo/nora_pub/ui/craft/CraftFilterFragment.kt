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
import com.blumenstreetdoo.nora_pub.ui.craft.CraftFilterState.Companion.MAX_ABV
import com.blumenstreetdoo.nora_pub.ui.craft.CraftFilterState.Companion.MAX_IBU
import com.blumenstreetdoo.nora_pub.ui.craft.CraftFilterState.Companion.MIN_ABV
import com.blumenstreetdoo.nora_pub.ui.craft.CraftFilterState.Companion.MIN_IBU
import com.google.android.material.slider.RangeSlider
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.NumberFormat

class CraftFilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()
    private val args: CraftFilterFragmentArgs by navArgs()

    private lateinit var initialFilter: CraftFilterState

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
        val currentFilter = args.filterState ?: CraftFilterState(
            minAbv = MIN_ABV,
            maxAbv = MAX_ABV,
            minIbu = MIN_IBU,
            maxIbu = MAX_IBU
        )
        initialFilter = currentFilter.copy()

        val countries = resources.getStringArray(R.array.country_list).toList()
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, countries)

        with(binding) {
            dropdownCountry.setAdapter(adapter)
            dropdownCountry.setText(currentFilter.country, false)

            setupSlider(sliderAbv, MIN_ABV, MAX_ABV, currentFilter.minAbv, currentFilter.maxAbv)
            setupSlider(sliderIbu, MIN_IBU, MAX_IBU, currentFilter.minIbu, currentFilter.maxIbu)

            btnApplyFilter.setOnClickListener { applyFilter() }
            btnResetFilter.setOnClickListener { resetFilter() }

            setupFilterChangeListener()
        }

        updateButtonVisibility()
    }

    private fun setupSlider(
        slider: RangeSlider,
        min: Double,
        max: Double,
        currentMin: Double?,
        currentMax: Double?
    ) {
        slider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getNumberInstance()
            format.maximumFractionDigits = 1
            format.format(value.toDouble())
        }
        slider.setValues(
            currentMin?.toFloat() ?: min.toFloat(),
            currentMax?.toFloat() ?: max.toFloat()
        )
    }

    private fun setupFilterChangeListener() {
        with(binding) {
            dropdownCountry.setOnItemClickListener { _, _, _, _ -> updateButtonVisibility() }
            sliderAbv.addOnChangeListener { _, _, _ -> updateButtonVisibility() }
            sliderIbu.addOnChangeListener { _, _, _ -> updateButtonVisibility() }
        }
    }

    private fun getCurrentFilterValues(): CraftFilterState {
        with(binding) {
            val selectedCountry = dropdownCountry.text.toString().takeIf { it.isNotEmpty() }
            val abvValues = sliderAbv.values
            val ibuValues = sliderIbu.values

            return CraftFilterState(
                searchQuery = craftViewModel.craftFilterState.value.searchQuery,
                country = selectedCountry,
                minAbv = abvValues.firstOrNull()?.toDouble(),
                maxAbv = abvValues.lastOrNull()?.toDouble(),
                minIbu = ibuValues.firstOrNull()?.toDouble(),
                maxIbu = ibuValues.lastOrNull()?.toDouble()
            )
        }
    }


    private fun updateButtonVisibility() {
        val newFilter = getCurrentFilterValues()
        val isFilterChanged = newFilter != initialFilter
        val isDefaultFilter = newFilter == craftViewModel.defaultFilterState

        binding.btnApplyFilter.visibility = if (isFilterChanged && !isDefaultFilter) View.VISIBLE else View.GONE
        binding.btnResetFilter.visibility = if (!isDefaultFilter) View.VISIBLE else View.GONE
    }

    private fun applyFilter() {
        val newFilter = getCurrentFilterValues()

        craftViewModel.updateFilter(newFilter)
        findNavController().popBackStack()
    }

    private fun resetFilter() {
        binding.dropdownCountry.setText("", false)
        binding.sliderAbv.setValues(MIN_ABV.toFloat(), MAX_ABV.toFloat())
        binding.sliderIbu.setValues(MIN_IBU.toFloat(), MAX_IBU.toFloat())

        craftViewModel.resetFilter()

        updateButtonVisibility()
    }
}
