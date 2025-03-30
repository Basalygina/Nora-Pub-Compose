package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class CraftViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = NUMBER_OF_PAGES

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> BeerListFragment.newInstance()
            else -> CansInFridgeFragment.newInstance()
        }
    }

    companion object {
        private const val NUMBER_OF_PAGES = 2
    }

}
