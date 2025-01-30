package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.FragmentFilterBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.NumberFormat

class CraftFilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()

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
        val countries = resources.getStringArray(R.array.country_list).toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, countries)
        with(binding) {
            dropdownCountry.setAdapter(adapter)
            sliderAbv.setLabelFormatter { value: Float ->
                val format = NumberFormat.getPercentInstance()
                format.maximumFractionDigits = 1
                format.format(value.toDouble()/100)
            }
            sliderIbu.setLabelFormatter { value: Float ->
                val format = NumberFormat.getIntegerInstance()
                format.maximumFractionDigits = 0
                format.format(value.toDouble())
            }
        }

    }
}