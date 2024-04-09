package com.ferechamitebeyli.journey.presentation.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferechamitebeyli.journey.databinding.FragmentBusJourneyIndexBinding
import com.ferechamitebeyli.journey.presentation.adapter.BusJourneyListAdapter
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.journey.presentation.viewmodel.BusJourneyIndexViewModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.Data
import com.ferechamitebeyli.ui.base.BaseFragment
import com.ferechamitebeyli.ui.util.UiHelpers.collectFlowWithFragmentLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusJourneyIndexFragment : BaseFragment<FragmentBusJourneyIndexBinding>(
    FragmentBusJourneyIndexBinding::inflate
) {

    private lateinit var busJourneyAdapter: BusJourneyListAdapter
    private val viewModel: BusJourneyIndexViewModel by viewModels()
    private val arguments: BusJourneyIndexFragmentArgs by navArgs()

    override fun setUpUi() {
        super.setUpUi()
        progressBar = binding.progressBar

        val originName = arguments.args?.originLocationModel?.name ?: ""
        val destinationName = arguments.args?.destinationLocationModel?.name ?: ""
        val departureDate = arguments.args?.departureDate

        binding.textViewBusJourneyIndexTitle.text = getString(
            com.ferechamitebeyli.ui.R.string.label_originToDestination,
            originName,
            destinationName
        )

        binding.textViewBusJourneyIndexDate.text = arguments.args?.departureDateForUi

        binding.recyclerViewBusJourneyIndex.apply {
            busJourneyAdapter = BusJourneyListAdapter(requireContext())
            adapter = busJourneyAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        viewModel.getBusJourneys(
            data = Data(
                departureDate = departureDate,
                destinationId = arguments.args?.destinationLocationModel?.id,
                originId = arguments.args?.originLocationModel?.id
            )
        )
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        binding.imageViewBusJourneyIndexBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    override fun observeFlows() {
        super.observeFlows()

        viewModel.getBusJourneysStateFlow.collectFlowWithFragmentLifecycle(
            this@BusJourneyIndexFragment
        ) { state ->
            when (state) {
                is JourneyResponseState.Error -> {
                    hideProgressBar()
                    showCustomDialog(
                        message = state.error?.asString(requireContext())
                            ?: getString(com.ferechamitebeyli.network.R.string.message_safeApiCall_operationFailed),
                        isInfoMessage = true
                    )
                }

                is JourneyResponseState.Idle -> {/* NO-OP */
                }

                is JourneyResponseState.Loading -> {
                    showProgressBar()
                }

                is JourneyResponseState.Success -> {
                    hideProgressBar()

                    state.data?.let {
                        busJourneyAdapter.submitList(it)
                    }
                }
            }

        }
    }
}