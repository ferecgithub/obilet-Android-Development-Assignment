package com.ferechamitebeyli.journey.presentation.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.ferechamitebeyli.journey.databinding.FragmentBusJourneyIndexBinding
import com.ferechamitebeyli.journey.presentation.adapter.BusJourneyListAdapter
import com.ferechamitebeyli.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusJourneyIndexFragment : BaseFragment<FragmentBusJourneyIndexBinding>(
    FragmentBusJourneyIndexBinding::inflate
) {

    private lateinit var busJourneyAdapter: BusJourneyListAdapter

    override fun setUpUi() {
        super.setUpUi()

        binding.recyclerViewBusJourneyIndex.apply {
            busJourneyAdapter = BusJourneyListAdapter(requireContext())
            adapter = busJourneyAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}