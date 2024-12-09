package com.blumenstreetdoo.nora_pub.ui.cansInFridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blumenstreetdoo.nora_pub.databinding.FragmentCansInFridgeBinding

class CansInFridgeFragment : Fragment() {

    private var _binding: FragmentCansInFridgeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cansInFridgeViewModel =
            ViewModelProvider(this).get(CansInFridgeViewModel::class.java)

        _binding = FragmentCansInFridgeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        cansInFridgeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment = CansInFridgeFragment()
    }
}