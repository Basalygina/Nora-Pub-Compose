package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.databinding.FragmentBeerOnTapBinding
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class BeerOnTapFragment : Fragment() {

    private var _binding: FragmentBeerOnTapBinding? = null
    private val binding get() = _binding!!
    private val beerAdapter by lazy { BeerAdapter(mutableListOf()) { onBeerClick(it) } }

    private fun onBeerClick(beer: Beer) {
        val action = CraftFragmentDirections.actionNavigationCraftToBeerDetailsFragment(beer.id)
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                craftViewModel.craftState.collectLatest { state ->
                    if (!isAdded || _binding == null) return@collectLatest

                    when (state) {
                        is CraftScreenState.Content -> showContent(state.fullBeerList)
                        is CraftScreenState.Error -> showError()
                        CraftScreenState.Loading -> showLoading()
                    }
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