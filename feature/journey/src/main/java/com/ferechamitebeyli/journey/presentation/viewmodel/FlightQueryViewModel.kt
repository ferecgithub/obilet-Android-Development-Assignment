package com.ferechamitebeyli.journey.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.domain.usecase.busquery.GetBusLocationsUseCase
import com.ferechamitebeyli.journey.domain.usecase.flightquery.ValidateFlightQueryInfoUseCase
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.network.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightQueryViewModel @Inject constructor(
    private val getBusLocationsUseCase: GetBusLocationsUseCase,
    private val validateFlightQueryInfoUseCase: ValidateFlightQueryInfoUseCase
) : ViewModel() {

    var departureDateForService: String = ""
    var arrivalDateForService: String = ""

    var currentOrigin: LocationDataUiModel? = null
    var currentDestination: LocationDataUiModel? = null

    private val _getBusLocationsStateFlow: MutableStateFlow<JourneyResponseState<List<LocationDataUiModel>>> =
        MutableStateFlow(
            JourneyResponseState.Idle()
        )
    val getBusLocationsStateFlow = _getBusLocationsStateFlow.asStateFlow()


    fun getBusLocations(data: String?, date: String = departureDateForService) =
        viewModelScope.launch {
            getBusLocationsUseCase(data, date).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        response.error?.let { uiText ->
                            _getBusLocationsStateFlow.update { JourneyResponseState.Error(text = uiText) }
                        }
                    }

                    is Resource.Loading -> {
                        _getBusLocationsStateFlow.update { JourneyResponseState.Loading() }
                    }

                    is Resource.Success -> {
                        response.data?.let { data ->
                            _getBusLocationsStateFlow.update { JourneyResponseState.Success(data) }
                        }
                    }
                }
            }
        }

    fun validateFlightQueryInformation(
        departureModel: LocationDataUiModel? = currentOrigin,
        destinationModel: LocationDataUiModel? = currentDestination,
        departureDate: String = departureDateForService,
        arrivalDate: String = arrivalDateForService
    ): Boolean {
        return if (departureModel == null || destinationModel == null) {
            false
        } else {
            validateFlightQueryInfoUseCase.invoke(
                departureModel, destinationModel, departureDate, arrivalDate
            )
        }
    }


}