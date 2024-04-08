package com.ferechamitebeyli.journey.presentation.fragment.query

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ferechamitebeyli.caching.model.LastQueryUiModel
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.FragmentBusQueryBinding
import com.ferechamitebeyli.journey.presentation.argument.JourneyNavArgument
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.journey.presentation.util.JourneyHelpers.validateCachedQuery
import com.ferechamitebeyli.journey.presentation.viewmodel.BusQueryViewModel
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

        viewModel.getCachedLastQuery()

        viewModel.getBusLocations(
            data = null
        )
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        binding.buttonBusQueryFindTicket.setOnClickListener {
            if (viewModel.validateBusQueryInformation()) {

                viewModel.cacheLastQuery(
                    viewModel.currentOrigin?.name,
                    viewModel.currentOrigin?.id,
                    viewModel.currentDestination?.name,
                    viewModel.currentDestination?.id,
                    viewModel.departureDateForService,
                    binding.textViewBusQueryDepartureDate.text?.trim()?.toString()
                )

                // BusJourneyIndexFragment' e navigation

            } else {
                showCustomDialog(
                    getString(com.ferechamitebeyli.ui.R.string.message_invalidInfoWarning),
                    isInfoMessage = true
                )
            }

        }

        binding.textViewBusQueryOrigin.setOnClickListener {
            //displayQueryDialog(isOrigin = true)
            navigateToQueryFragment(isOrigin = true)
        }

        binding.textViewBusQueryDestination.setOnClickListener {
            //displayQueryDialog(isOrigin = false)
            navigateToQueryFragment(isOrigin = false)
        }

        binding.imageViewBusQuerySwapOriginAndDestination.setOnClickListener {

            val temporaryModel: LocationDataUiModel? = viewModel.currentOrigin
            viewModel.currentOrigin = viewModel.currentDestination
            viewModel.currentDestination = temporaryModel

            binding.textViewBusQueryOrigin.text = viewModel.currentOrigin?.name
            binding.textViewBusQueryDestination.text = viewModel.currentDestination?.name

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

                    val isThereAnyLastCachedQuery =
                        validateCachedQuery(viewModel.getCachedLastQueryStateFlow.value)

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
            this@BusQueryFragment
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

                    binding.textViewBusQueryOrigin.text = query.originName
                    binding.textViewBusQueryDestination.text = query.destinationName

                    binding.textViewBusQueryDepartureDate.text = query.departureDateForUi
                    query.departureDateForService?.let {
                        viewModel.departureDateForService = it
                    }
                }
            }
        }
    }


    private fun navigateToQueryFragment(isOrigin: Boolean) {
        viewModel.getBusLocationsStateFlow.value.data?.let { list ->

            parentFragmentManager.fragments.forEach {
                Log.d("BQFR", "${it?.javaClass?.simpleName}")
            }

            val args = JourneyNavArgument(
                locationModelList = list,
                isOrigin = isOrigin
            )

            /*
            val action = TravelQueryFragmentDirections.actionTravelQueryFragmentToQueryFragment(
                args = args
            )

             */

            val action = BusQueryFragmentDirections.actionBusQueryFragmentToQueryFragment(
                args = args
            )

            //val viewPagerContainerFragment = parentFragmentManager.findFragmentById(R.id.travelQueryFragment)


            findNavController().navigate(action)

        }

    }

    private fun populateInitialOriginAndDestination(
        origin: LocationDataUiModel?,
        destination: LocationDataUiModel?
    ) {
        binding.textViewBusQueryOrigin.text =
            origin?.name ?: getString(com.ferechamitebeyli.ui.R.string.message_pleaseEnterAnOrigin)
        viewModel.currentOrigin = origin
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

}