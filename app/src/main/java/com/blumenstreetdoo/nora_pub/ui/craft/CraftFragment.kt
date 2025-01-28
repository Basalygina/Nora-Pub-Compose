package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.FragmentCraftBinding
import com.google.android.material.tabs.TabLayoutMediator

class CraftFragment : Fragment() {

    private var _binding: FragmentCraftBinding? = null
    private val binding get() = _binding!!
    private var tabMediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCraftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.craftViewPager.adapter = CraftViewPagerAdapter(childFragmentManager, lifecycle)
        tabMediator =
            TabLayoutMediator(binding.craftTabLayout, binding.craftViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.title_beer_on_tap)
                    1 -> tab.text = getString(R.string.title_cans_in_fridge)
                }
            }.apply { attach() }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator?.detach()
        tabMediator = null
        _binding = null
    }
}