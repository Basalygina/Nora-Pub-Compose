package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.databinding.FragmentCraftBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CraftFragment : Fragment() {

    private var _binding: FragmentCraftBinding? = null
    private val binding get() = _binding!!
    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()
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
        with(binding) {
            craftViewPager.adapter = CraftViewPagerAdapter(childFragmentManager, lifecycle)
            tabMediator =
                TabLayoutMediator(craftTabLayout, craftViewPager) { tab, position ->
                    when (position) {
                        0 -> tab.text = getString(R.string.title_beer_on_tap)
                        1 -> tab.text = getString(R.string.title_cans_in_fridge)
                    }
                }.apply { attach() }

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!s.isNullOrBlank()) {
                        clearTextButton.visibility = View.VISIBLE
                    } else {
                        clearTextButton.visibility = View.GONE
                    }
                    craftViewModel.updateFilter(
                        craftViewModel.craftFilterState.value.copy(
                            searchQuery = s.toString()
                        )
                    )

                }

                override fun afterTextChanged(s: Editable?) {}
            }
            searchEditText.addTextChangedListener(textWatcher)
            clearTextButton.setOnClickListener {
                searchEditText.setText("")
            }
            filterIcon.setOnClickListener {
                val action = CraftFragmentDirections.actionNavigationCraftToCraftFilterFragment()
                findNavController().navigate(action)
            }
            if (craftViewModel.craftFilterState.value.activeFilterCount > 0) {
                filterCount.setText(craftViewModel.craftFilterState.value.activeFilterCount)
                filterCount.visibility = View.VISIBLE
            } else {
                filterCount.visibility = View.GONE
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