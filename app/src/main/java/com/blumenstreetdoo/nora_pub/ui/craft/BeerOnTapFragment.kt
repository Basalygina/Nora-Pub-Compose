package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.databinding.FragmentBeerOnTapBinding
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class BeerOnTapFragment : Fragment() {

    private var _binding: FragmentBeerOnTapBinding? = null
    private val binding get() = _binding!!
    private val beerAdapter by lazy { BeerAdapter(mutableListOf()) { onBeerClick(it) } }

    private fun onBeerClick(beer: Beer) {
        val action = CraftFragmentDirections.actionNavigationCraftToBeerDetailsFragment(beer)
        findNavController().navigate(action)
    }

    private val craftViewModel: CraftViewModel by activityViewModel<CraftViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeerOnTapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            craftViewModel.craftState.collect { state ->
                when (state) {
                    CraftScreenState.Loading -> showLoading()
                    is CraftScreenState.Content -> showContent(state.fullBeerList)
                    is CraftScreenState.Error -> showError()
                }
            }
        }
        binding.recycler.adapter = beerAdapter
    }

    private fun showLoading() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            recycler.visibility = View.GONE
            icError.visibility = View.GONE
            messageError.visibility = View.GONE
        }
    }

    private fun showContent(fullBeerList: List<Beer>) {
        with(binding) {
            progressBar.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            icError.visibility = View.GONE
            messageError.visibility = View.GONE
        }
        beerAdapter.updateBeerList(fullBeerList, DrinkType.TAP_BEER)
    }

    private fun showError() {
        with(binding) {
            progressBar.visibility = View.GONE
            recycler.visibility = View.GONE
            icError.visibility = View.VISIBLE
            messageError.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment = BeerOnTapFragment()
    }
}