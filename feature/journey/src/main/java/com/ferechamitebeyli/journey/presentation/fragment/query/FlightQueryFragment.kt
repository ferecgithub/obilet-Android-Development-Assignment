package com.ferechamitebeyli.journey.presentation.fragment.query

import android.util.Log
import androidx.fragment.app.viewModels
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.databinding.FragmentFlightQueryBinding
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.journey.presentation.util.JourneyHelpers
import com.ferechamitebeyli.journey.presentation.util.JourneyHelpers.validateCachedQuery
import com.ferechamitebeyli.journey.presentation.viewmodel.FlightQueryViewModel
import com.ferechamitebeyli.ui.R
import com.ferechamitebeyli.ui.base.BaseFragment
import com.ferechamitebeyli.ui.util.UiHelpers
import com.ferechamitebeyli.ui.util.UiHelpers.collectFlowWithFragmentLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightQueryFragment : BaseFragment<FragmentFlightQueryBinding>(
    FragmentFlightQueryBinding::inflate
) {
    private val viewModel: FlightQueryViewModel by viewModels()

    override fun setUpUi() {
        super.setUpUi()
        progressBar = binding.progressBar

        setInitialDates()

        // Setting initial passenger value to 0
        binding.textViewFlightQueryPassenger.text =
            getString(com.ferechamitebeyli.ui.R.string.label_passengerWithArgs, "0")

        viewModel.getCachedLastQuery()

        viewModel.getBusLocations(
            data = null
        )
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        binding.buttonFlightQueryFindTicket.setOnClickListener {
            if (viewModel.validateFlightQueryInformation()) {
                viewModel.cacheLastQuery(
                    viewModel.currentOrigin?.name,
                    viewModel.currentOrigin?.id,
                    viewModel.currentDestination?.name,
                    viewModel.currentDestination?.id,
                    viewModel.departureDateForService,
                    binding.textViewFlightQueryDepartureDate.text?.trim()?.toString()
                )

                // BusJourneyIndexFragment' e navigation
            } else {
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    getString(R.string.message_invalidInfoWarning),
                    Snackbar.LENGTH_LONG
                ).show()
            }


        }

        binding.textViewFlightQueryDepartureDate.setOnClickListener {
            UiHelpers.showDatePicker(
                requireContext(),
                binding.textViewFlightQueryDepartureDate
            ) {
                viewModel.departureDateForService = it.dateForService
            }
        }

        binding.textViewFlightQueryArrivalDate.setOnClickListener {
            UiHelpers.showDatePicker(
                requireContext(),
                binding.textViewFlightQueryArrivalDate
            ) {
                viewModel.arrivalDateForService = it.dateForService
            }
        }

        binding.imageViewFlightQuerySwapOriginAndDestination.setOnClickListener {
            val temporaryModel: LocationDataUiModel? = viewModel.currentOrigin
            viewModel.currentOrigin = viewModel.currentDestination
            viewModel.currentDestination = temporaryModel

            binding.textViewFlightQueryOrigin.text = viewModel.currentOrigin?.name
            binding.textViewFlightQueryDestination.text = viewModel.currentDestination?.name

            UiHelpers.startRotationAnimation(
                binding.imageViewFlightQuerySwapOriginAndDestination,
                requireContext()
            )
        }
    }

    override fun observeFlows() {
        super.observeFlows()

        viewModel.getBusLocationsStateFlow.collectFlowWithFragmentLifecycle(
            this@FlightQueryFragment
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

                    val isThereAnyLastCachedQuery =
                        JourneyHelpers.validateCachedQuery(viewModel.getCachedLastQueryStateFlow.value)

                    if (isThereAnyLastCachedQuery) {
                        val originModel = LocationDataUiModel(
                            id = viewModel.getCachedLastQueryStateFlow.value?.originId,
                            name = viewModel.getCachedLastQueryStateFlow.value?.originName,
                            parentId = null,
                            type = null,
                            cityId = null,
                            cityName = null
                        )
                        val destinationModel = LocationDataUiModel(
                            id = viewModel.getCachedLastQueryStateFlow.value?.destinationId,
                            name = viewModel.getCachedLastQueryStateFlow.value?.destinationName,
                            parentId = null,
                            type = null,
                            cityId = null,
                            cityName = null
                        )
                        populateInitialOriginAndDestination(
                            originModel,
                            destinationModel,
                        )
                    } else {
                        populateInitialOriginAndDestination(
                            state.data?.first(),
                            state.data?.last(),
                        )
                    }
                }
            }
        }

        viewModel.getCachedLastQueryStateFlow.collectFlowWithFragmentLifecycle(
            this@FlightQueryFragment
        ) { query ->
            query?.let {

                if (validateCachedQuery(it)) {
                    val dummyModel = LocationDataUiModel(null, null, null, null, null, null)

                    viewModel.currentOrigin = dummyModel.copy(
                        id = query.originId,
                        name = query.originName
                    )
                    viewModel.currentDestination = dummyModel.copy(
                        id = query.destinationId,
                        name = query.destinationName
                    )

                    binding.textViewFlightQueryOrigin.text = query.originName
                    binding.textViewFlightQueryDestination.text = query.destinationName

                    binding.textViewFlightQueryDepartureDate.text = query.departureDateForUi
                    query.departureDateForService?.let {
                        viewModel.departureDateForService = it
                    }
                }
            }
        }
    }

    private fun populateInitialOriginAndDestination(
        origin: LocationDataUiModel?,
        destination: LocationDataUiModel?
    ) {
        binding.textViewFlightQueryOrigin.text =
            origin?.name ?: getString(com.ferechamitebeyli.ui.R.string.message_pleaseEnterAnOrigin)
        viewModel.currentOrigin = origin
        binding.textViewFlightQueryDestination.text = destination?.name
            ?: getString(com.ferechamitebeyli.ui.R.string.message_pleaseEnterADestination)
        viewModel.currentDestination = destination
    }

    private fun setInitialDates(isTomorrow: Boolean = true) {
        val departureDate = UiHelpers.getFormattedDateForQuickSelection(
            isTomorrow = isTomorrow.not()
        )
        val arrivalDate = UiHelpers.getFormattedDateForQuickSelection(
            isTomorrow = isTomorrow
        )
        binding.textViewFlightQueryDepartureDate.text = departureDate.dateForUi
        viewModel.departureDateForService = departureDate.dateForService

        binding.textViewFlightQueryArrivalDate.text = arrivalDate.dateForUi
        viewModel.arrivalDateForService = arrivalDate.dateForService
    }
}