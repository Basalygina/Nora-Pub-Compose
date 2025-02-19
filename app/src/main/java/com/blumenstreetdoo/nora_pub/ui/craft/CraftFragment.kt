package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.FragmentCraftBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CraftFragment : Fragment() {
    private var _binding: FragmentCraftBinding? = null
    private val binding get() = _binding!!
    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()
    private var tabMediator: TabLayoutMediator? = null
    private var badgeDrawable: BadgeDrawable? = null

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

        setupViewPager()
        setupSearchInput()
        setupFilterButton()
        setupBadge()

        viewLifecycleOwner.lifecycleScope.launch {
            craftViewModel.craftFilterState.collect { state ->
                updateBadge(state.activeFilterCount)
            }
        }
    }

    private fun setupViewPager() {
        with(binding) {
            craftViewPager.adapter = CraftViewPagerAdapter(childFragmentManager, lifecycle)
            tabMediator = TabLayoutMediator(craftTabLayout, craftViewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.title_beer_on_tap)
                    1 -> getString(R.string.title_cans_in_fridge)
                    else -> ""
                }
            }.apply { attach() }
        }
    }

    private fun setupSearchInput() {
        binding.searchEditText.addTextChangedListener { text ->
            val query = text?.toString()?.trim()

            craftViewModel.updateFilter(
                craftViewModel.craftFilterState.value.copy(searchQuery = query)
            )
            binding.clearTextButton.visibility =
                if (!query.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        binding.clearTextButton.setOnClickListener {
            binding.searchEditText.setText("")
        }
    }

    private fun setupFilterButton() {
        binding.filterIcon.setOnClickListener {
            val action =
                CraftFragmentDirections.actionNavigationCraftToCraftFilterFragment(
                    craftViewModel.craftFilterState.value
                )
            findNavController().navigate(action)
        }
    }

    @OptIn(ExperimentalBadgeUtils::class)
    private fun setupBadge() {
        if (badgeDrawable == null) {
            badgeDrawable = BadgeDrawable.create(requireContext()).apply {
                backgroundColor = ContextCompat.getColor(requireContext(), R.color.yellow_accent)
                badgeTextColor = ContextCompat.getColor(requireContext(), R.color.white)
                isVisible = false
            }
            BadgeUtils.attachBadgeDrawable(badgeDrawable!!, binding.filterIcon)
        }
    }

    @OptIn(ExperimentalBadgeUtils::class)
    private fun updateBadge(filterCount: Int) {

        if (badgeDrawable == null) setupBadge()

        badgeDrawable?.apply {
            number = filterCount
            isVisible = filterCount > 0
        }

        if (filterCount == 0) {
            binding.filterIcon.post {
                BadgeUtils.detachBadgeDrawable(badgeDrawable, binding.filterIcon)
            }
        } else {
            binding.filterIcon.post {
                BadgeUtils.attachBadgeDrawable(badgeDrawable!!, binding.filterIcon)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator?.detach()
        tabMediator = null
        _binding = null
    }
}
