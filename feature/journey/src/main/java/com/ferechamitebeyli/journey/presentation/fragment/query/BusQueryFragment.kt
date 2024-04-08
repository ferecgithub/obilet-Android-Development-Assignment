package com.ferechamitebeyli.journey.presentation.fragment.query

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.databinding.FragmentBusQueryBinding
import com.ferechamitebeyli.journey.presentation.argument.JourneyNavArgument
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
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

        viewModel.getBusLocations(
            data = null
        )
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

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

                    populateInitialOriginAndDestination(
                        state.data?.first(),
                        state.data?.last(),
                    )
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