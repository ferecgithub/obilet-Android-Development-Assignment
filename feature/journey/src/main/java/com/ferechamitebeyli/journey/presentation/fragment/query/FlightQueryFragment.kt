package com.ferechamitebeyli.journey.presentation.fragment.query

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.databinding.FragmentFlightQueryBinding
import com.ferechamitebeyli.journey.presentation.argument.JourneyNavArgument
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.journey.presentation.viewmodel.TravelQueryViewModel
import com.ferechamitebeyli.navigation.safeNavigate
import com.ferechamitebeyli.ui.R
import com.ferechamitebeyli.ui.base.BaseFragment
import com.ferechamitebeyli.ui.util.UiHelpers
import com.ferechamitebeyli.ui.util.UiHelpers.collectFlowWithFragmentLifecycle
import com.ferechamitebeyli.ui.util.UiHelpers.getFormattedDateForArrivalDateAccordingToDepartureDate
import com.ferechamitebeyli.ui.util.UiHelpers.showDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightQueryFragment : BaseFragment<FragmentFlightQueryBinding>(
    FragmentFlightQueryBinding::inflate
) {
    private val viewModel: TravelQueryViewModel by viewModels({ requireParentFragment() })

    override fun setUpUi() {
        super.setUpUi()
        progressBar = binding.progressBar

        setInitialDates()

        // Setting initial passenger value to 0
        binding.textViewFlightQueryPassenger.text =
            getString(R.string.label_passengerWithArgs, "0")

        viewModel.getCachedLastQuery()

        viewModel.getBusLocations(
            data = null
        )
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        binding.buttonFlightQueryFindTicket.setOnClickListener {
            if (viewModel.validateFlightQueryInformation()) {
                viewModel.cacheLastQueriedOrigin(
                    viewModel.currentOrigin?.name,
                    viewModel.currentOrigin?.id
                )
                viewModel.cacheLastQueriedDestination(
                    viewModel.currentDestination?.name,
                    viewModel.currentDestination?.id,
                )
                viewModel.cacheLastQueriedDepartureDate(
                    viewModel.departureDateForService,
                    binding.textViewFlightQueryDepartureDate.text?.trim()?.toString()
                )

                navigateToBusJourneyIndexFragment()
            } else {
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    getString(R.string.message_invalidInfoWarning),
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }

        binding.textViewFlightQueryOrigin.setOnClickListener {
            navigateToQueryFragment(isOrigin = true)
        }

        binding.textViewFlightQueryDestination.setOnClickListener {
            navigateToQueryFragment(isOrigin = false)
        }

        binding.buttonFlightQueryAddPassengers.setOnClickListener {
            showBottomSheet()
        }

        binding.textViewFlightQueryDepartureDate.setOnClickListener {
            showDatePicker(
                requireContext(),
                binding.textViewFlightQueryDepartureDate
            ) {
                viewModel.departureDateForService = it.dateForService
                viewModel.departureDateForUi = it.dateForUi
                viewModel.cacheLastQueriedDepartureDate(
                    departureDateForService = it.dateForService,
                    departureDateForUi = it.dateForUi
                )
                binding.textViewFlightQueryArrivalDate.text = getFormattedDateForArrivalDateAccordingToDepartureDate(
                    departureDate = viewModel.departureDateForUi
                ).dateForUi
            }
        }

        binding.textViewFlightQueryArrivalDate.setOnClickListener {
            showDatePicker(
                requireContext(),
                binding.textViewFlightQueryArrivalDate
            ) {
                viewModel.arrivalDateForService = it.dateForService
                viewModel.arrivalDateForUi = it.dateForUi
            }
        }

        binding.imageViewFlightQuerySwapOriginAndDestination.setOnClickListener {
            val temporaryModel: LocationDataUiModel? = viewModel.currentOrigin
            viewModel.currentOrigin = viewModel.currentDestination
            viewModel.currentDestination = temporaryModel

            binding.textViewFlightQueryOrigin.text = viewModel.currentOrigin?.name
            binding.textViewFlightQueryDestination.text = viewModel.currentDestination?.name

            viewModel.cacheLastQueriedOrigin(
                originName = viewModel.currentOrigin?.name,
                originId = viewModel.currentOrigin?.id
            )

            viewModel.cacheLastQueriedDestination(
                destinationName = viewModel.currentDestination?.name,
                destinationId = viewModel.currentDestination?.id
            )

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

                    viewModel.exposeUiDataForFlightQueryFragment()
                }
            }


        }

        viewModel.flightQueryFragmentUiStateFlow.collectFlowWithFragmentLifecycle(
            this@FlightQueryFragment
        ) { state ->
            when (state) {
                is JourneyResponseState.Error -> {}
                is JourneyResponseState.Idle -> {}
                is JourneyResponseState.Loading -> {}
                is JourneyResponseState.Success -> {
                    binding.textViewFlightQueryDestination.text = state.data?.destinationName
                    //uiState.destinationId
                    binding.textViewFlightQueryOrigin.text = state.data?.originName
                    //uiState.originId
                    binding.textViewFlightQueryDepartureDate.text = state.data?.departureDateForUi
                    binding.textViewFlightQueryArrivalDate.text = state.data?.arrivalDateForUi
                }
            }
        }
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

    private fun showBottomSheet() {
        val bottomSheetFragment = PassengerSelectionDialogFragment {
            binding.textViewFlightQueryPassenger.text = getString(R.string.label_passengerWithArgs, it.toString())
        }
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    private fun navigateToQueryFragment(isOrigin: Boolean) {
        viewModel.getBusLocationsStateFlow.value.data?.let { list ->

            val args = if (viewModel.arguments == null) {
                JourneyNavArgument(
                    locationModelList = list,
                    isOrigin = isOrigin,
                    lastSelectedTabIndex = 1
                )
            } else {
                viewModel.arguments?.copy(
                    locationModelList = list,
                    isOrigin = isOrigin,
                    lastSelectedTabIndex = 1
                )
            }

            val action =
                TravelQueryFragmentDirections.actionTravelQueryFragmentToQueryFragment(args)
            findNavController().safeNavigate(action)
        }

    }

    private fun navigateToBusJourneyIndexFragment() {
        val args = if (viewModel.arguments == null) {
            JourneyNavArgument(
                originLocationModel = viewModel.currentOrigin,
                destinationLocationModel = viewModel.currentDestination,
                departureDate = viewModel.departureDateForService,
                departureDateForUi = viewModel.departureDateForUi,
                lastSelectedTabIndex = 1
            )
        } else {
            viewModel.arguments?.copy(
                originLocationModel = viewModel.currentOrigin,
                destinationLocationModel = viewModel.currentDestination,
                departureDate = viewModel.departureDateForService,
                departureDateForUi = viewModel.departureDateForUi,
                lastSelectedTabIndex = 1
            )
        }

        val action =
            TravelQueryFragmentDirections.actionTravelQueryFragmentToBusJourneyIndexFragment(args)
        findNavController().safeNavigate(action)
    }
}