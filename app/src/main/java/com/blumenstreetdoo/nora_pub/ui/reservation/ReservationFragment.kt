package com.blumenstreetdoo.nora_pub.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.FragmentReservationBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReservationFragment : Fragment() {
    private var _binding: FragmentReservationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReservationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReservationBinding.bind(view)

        setupListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reservationState.collect { state ->
                binding.btnSelectDate.text = state.date ?: getString(R.string.select_date)
                binding.btnSelectTime.text = state.time ?: getString(R.string.select_time)
                binding.etGuestCount.setText(state.guests.toString())

                when (state.status) {
                    is ReservationState.Status.Loading -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.loading_reservation), Toast.LENGTH_SHORT).show()
                    }
                    is ReservationState.Status.Success -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.reservation_success), Toast.LENGTH_LONG).show()
                    }
                    is ReservationState.Status.Error -> {
                        val message = state.status.message ?: state.status.messageRes?.let { getString(it) }
                        message?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnSelectDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .build()
            datePicker.show(parentFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                viewModel.setDate(sdf.format(Date(it)))
            }
        }

        binding.btnSelectTime.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.show(parentFragmentManager, "TIME_PICKER")
            timePicker.addOnPositiveButtonClickListener {
                viewModel.setTime("${timePicker.hour}:${timePicker.minute}")
            }
        }

        binding.etGuestCount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val count = binding.etGuestCount.text.toString().toIntOrNull() ?: 2
                viewModel.setGuestCount(count)
            }
        }

        binding.btnSubmitReservation.setOnClickListener {
            viewModel.submitReservation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}