package com.ferechamitebeyli.journey.presentation.fragment.query

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.databinding.FragmentBusQueryBinding
import com.ferechamitebeyli.journey.presentation.argument.JourneyNavArgument
import com.ferechamitebeyli.journey.presentation.state.BusQueryQuickSelectionButtonState
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.journey.presentation.viewmodel.TravelQueryViewModel
import com.ferechamitebeyli.navigation.safeNavigate
import com.ferechamitebeyli.ui.base.BaseFragment
import com.ferechamitebeyli.ui.util.UiHelpers.collectFlowWithFragmentLifecycle
import com.ferechamitebeyli.ui.util.UiHelpers.getFormattedDateForQuickSelection
import com.ferechamitebeyli.ui.util.UiHelpers.showDatePicker
import com.ferechamitebeyli.ui.util.UiHelpers.startRotationAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusQueryFragment : BaseFragment<FragmentBusQueryBinding>(
    FragmentBusQueryBinding::inflate
) {
    private val viewModel: TravelQueryViewModel by viewModels({ requireParentFragment() })

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

        viewModel.getCachedLastQuery()

        viewModel.getBusLocations(
            data = null
        )
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        binding.buttonBusQueryFindTicket.setOnClickListener {
            if (viewModel.validateBusQueryInformation()) {

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
                    binding.textViewBusQueryDepartureDate.text?.trim()?.toString()
                )

                navigateToBusJourneyIndexFragment()

            } else {
                showCustomDialog(
                    getString(com.ferechamitebeyli.ui.R.string.message_invalidInfoWarning),
                    isInfoMessage = true
                )
            }

        }

        binding.textViewBusQueryOrigin.setOnClickListener {
            navigateToQueryFragment(isOrigin = true)
        }

        binding.textViewBusQueryDestination.setOnClickListener {
            navigateToQueryFragment(isOrigin = false)
        }

        binding.imageViewBusQuerySwapOriginAndDestination.setOnClickListener {

            val temporaryModel: LocationDataUiModel? = viewModel.currentOrigin
            viewModel.currentOrigin = viewModel.currentDestination
            viewModel.currentDestination = temporaryModel

            binding.textViewBusQueryOrigin.text = viewModel.currentOrigin?.name
            binding.textViewBusQueryDestination.text = viewModel.currentDestination?.name

            viewModel.cacheLastQueriedOrigin(
                viewModel.currentOrigin?.name,
                viewModel.currentOrigin?.id
            )
            viewModel.cacheLastQueriedDestination(
                viewModel.currentDestination?.name,
                viewModel.currentDestination?.id,
            )

            startRotationAnimation(
                binding.imageViewBusQuerySwapOriginAndDestination,
                requireContext()
            )
        }

        binding.textViewBusQueryDepartureDate.setOnClickListener {
            showDatePicker(
                requireContext(),
                binding.textViewBusQueryDepartureDate
            ) {
                viewModel.departureDateForService = it.dateForService
                viewModel.departureDateForUi = it.dateForUi
                viewModel.cacheLastQueriedDepartureDate(
                    departureDateForService = it.dateForService,
                    departureDateForUi = it.dateForUi
                )
            }
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

                    viewModel.exposeUiDataForBusQueryFragment()
                }
            }

        }

        viewModel.busQueryFragmentUiStateFlow.collectFlowWithFragmentLifecycle(
            this@BusQueryFragment
        ) { state ->
            when (state) {
                is JourneyResponseState.Error -> {}
                is JourneyResponseState.Idle -> {}
                is JourneyResponseState.Loading -> {}
                is JourneyResponseState.Success -> {
                    binding.textViewBusQueryDestination.text = state.data?.destinationName
                    //uiState.destinationId
                    binding.textViewBusQueryOrigin.text = state.data?.originName
                    //uiState.originId
                    binding.textViewBusQueryDepartureDate.text = state.data?.buttonState?.second
                    when (state.data?.buttonState?.first) {
                        BusQueryQuickSelectionButtonState.TODAY_CLICKED -> {
                            binding.radioButtonBusQueryToday.isChecked = true
                        }

                        BusQueryQuickSelectionButtonState.TOMORROW_CLICKED -> {
                            binding.radioButtonBusQueryTomorrow.isChecked = true
                        }

                        BusQueryQuickSelectionButtonState.NOTHING_CLICKED -> {
                            binding.radioButtonBusQueryToday.isChecked = false
                            binding.radioButtonBusQueryTomorrow.isChecked = true
                        }

                        null -> {
                            binding.radioButtonBusQueryToday.isChecked = false
                            binding.radioButtonBusQueryTomorrow.isChecked = true
                        }
                    }
                }
            }
        }
    }


    private fun populateInitialOrigin(
        origin: LocationDataUiModel?
    ) {
        binding.textViewBusQueryOrigin.text =
            origin?.name
                ?: getString(com.ferechamitebeyli.ui.R.string.message_pleaseEnterAnOrigin)
        viewModel.currentOrigin = origin
    }

    private fun populateInitialDestination(
        destination: LocationDataUiModel?
    ) {
        binding.textViewBusQueryDestination.text = destination?.name
            ?: getString(com.ferechamitebeyli.ui.R.string.message_pleaseEnterADestination)
        viewModel.currentDestination = destination
    }

    private fun setDateForQuickSelection(isTomorrow: Boolean) {
        val date = getFormattedDateForQuickSelection(
            isTomorrow = isTomorrow
        )
        binding.textViewBusQueryDepartureDate.text = date.dateForUi
        viewModel.departureDateForService = date.dateForService

    }

    private fun navigateToQueryFragment(isOrigin: Boolean) {
        viewModel.getBusLocationsStateFlow.value.data?.let { list ->

            val args = if (viewModel.arguments == null) {
                JourneyNavArgument(
                    locationModelList = list,
                    isOrigin = isOrigin,
                    lastSelectedTabIndex = 0
                )
            } else {
                viewModel.arguments?.copy(
                    locationModelList = list,
                    isOrigin = isOrigin,
                    lastSelectedTabIndex = 0
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
                lastSelectedTabIndex = 0
            )
        } else {
            viewModel.arguments?.copy(
                originLocationModel = viewModel.currentOrigin,
                destinationLocationModel = viewModel.currentDestination,
                departureDate = viewModel.departureDateForService,
                departureDateForUi = viewModel.departureDateForUi,
                lastSelectedTabIndex = 0
            )
        }

        val action =
            TravelQueryFragmentDirections.actionTravelQueryFragmentToBusJourneyIndexFragment(
                args
            )
        findNavController().safeNavigate(action)
    }
}