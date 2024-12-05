package com.blumenstreetdoo.nora_pub.ui.beerOnTap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blumenstreetdoo.nora_pub.databinding.FragmentBeerOnTapBinding

class beerOnTapFragment : Fragment() {

    private var _binding: FragmentBeerOnTapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val beerOnTapViewModel =
            ViewModelProvider(this).get(beerOnTapViewModel::class.java)

        _binding = FragmentBeerOnTapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        beerOnTapViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}