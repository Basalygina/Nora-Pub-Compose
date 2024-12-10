package com.blumenstreetdoo.nora_pub.ui.craft.beerOnTap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blumenstreetdoo.nora_pub.databinding.FragmentBeerOnTapBinding

class BeerOnTapFragment : Fragment() {

    private var _binding: FragmentBeerOnTapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val beerOnTapViewModel =
            ViewModelProvider(this).get(BeerOnTapViewModel::class.java)

        _binding = FragmentBeerOnTapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        beerOnTapViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment = BeerOnTapFragment()
    }
}