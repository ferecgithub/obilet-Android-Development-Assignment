package com.ferechamitebeyli.journey.presentation.fragment.query

import androidx.fragment.app.viewModels
import com.ferechamitebeyli.data.model.journey.JourneyDataUiModel
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.FragmentBusQueryBinding
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.journey.presentation.viewmodel.BusQueryViewModel
import com.ferechamitebeyli.ui.base.BaseFragment
import com.ferechamitebeyli.ui.util.UiHelpers.collectFlowWithFragmentLifecycle
import com.ferechamitebeyli.ui.util.UiHelpers.getFormattedDateForQuickSelection
import com.ferechamitebeyli.ui.util.UiHelpers.startRotationAnimation
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusQueryFragment : BaseFragment<FragmentBusQueryBinding>(
    FragmentBusQueryBinding::inflate
) {
    private val viewModel: BusQueryViewModel by viewModels()

    override fun setUpUi() {
        super.setUpUi()
        progressBar = binding.progressBar

        // Set default date as tomorrow
        setDateForQuickSelection(isTomorrow = true)

        // Assigning date strings for Ui and to a variable in ViewModel to send to the service later.
        binding.radioButtonBusQueryToday.setOnCheckedChangeListener { _, isChecked ->
            setDateForQuickSelection(isChecked.not())
        }

        binding.radioButtonBusQueryTomorrow.setOnCheckedChangeListener { _, isChecked ->
            setDateForQuickSelection(isChecked)
        }

        viewModel.getBusLocations(
            data = null
        )
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        binding.imageViewBusQuerySwapOriginAndDestination.setOnClickListener {

            val temporaryModel: LocationDataUiModel? = viewModel.currentOrigin
            viewModel.currentOrigin = viewModel.currentDestination
            viewModel.currentDestination = temporaryModel

            binding.textViewBusQueryOrigin.text = viewModel.currentOrigin?.name
            binding.textViewBusQueryDestination.text = viewModel.currentDestination?.name

            startRotationAnimation(binding.imageViewBusQuerySwapOriginAndDestination, requireContext())
        }
    }

    override fun observeFlows() {
        super.observeFlows()

        viewModel.getBusLocationsStateFlow.collectFlowWithFragmentLifecycle(
            this@BusQueryFragment
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

                    populateInitialOriginAndDestination(
                        state.data?.first(),
                        state.data?.last(),
                    )
                }
            }
        }
    }

    private fun populateInitialOriginAndDestination(origin: LocationDataUiModel?, destination: LocationDataUiModel?) {
        binding.textViewBusQueryOrigin.text = origin?.name ?: getString(com.ferechamitebeyli.ui.R.string.message_pleaseEnterAnOrigin)
        viewModel.currentOrigin = origin
        binding.textViewBusQueryDestination.text = destination?.name ?: getString(com.ferechamitebeyli.ui.R.string.message_pleaseEnterADestination)
        viewModel.currentDestination = destination
    }

    private fun setDateForQuickSelection(isTomorrow: Boolean) {
        val date = getFormattedDateForQuickSelection(
            isTomorrow = isTomorrow
        )
        binding.textViewBusQueryDepartureDate.text = date.dateForUi
        viewModel.departureDateForService = date.dateForService
    }


}