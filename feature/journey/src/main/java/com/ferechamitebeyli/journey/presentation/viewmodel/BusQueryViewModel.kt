package com.ferechamitebeyli.journey.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferechamitebeyli.caching.model.LastQueryUiModel
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.domain.usecase.busquery.GetBusLocationsUseCase
import com.ferechamitebeyli.journey.domain.usecase.busquery.ValidateBusQueryInfoUseCase
import com.ferechamitebeyli.journey.domain.usecase.common.CacheLastQueryUseCase
import com.ferechamitebeyli.journey.domain.usecase.common.GetCachedLastQueryUseCase
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.network.util.Resource
import com.ferechamitebeyli.ui.util.UiHelpers
import com.ferechamitebeyli.ui.util.UiHelpers.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusQueryViewModel @Inject constructor(
    private val getBusLocationsUseCase: GetBusLocationsUseCase,
    private val getCachedLastQueryUseCase: GetCachedLastQueryUseCase,
    private val cacheLastQueryUseCase: CacheLastQueryUseCase,
    private val validateBusQueryInfoUseCase: ValidateBusQueryInfoUseCase
) : ViewModel() {

    private val currentDate = getCurrentDateTime()

    var departureDateForService: String = ""

    var currentOrigin: LocationDataUiModel? = null
    var currentDestination: LocationDataUiModel? = null

    private val _getBusLocationsStateFlow: MutableStateFlow<JourneyResponseState<List<LocationDataUiModel>>> =
        MutableStateFlow(
            JourneyResponseState.Idle()
        )
    val getBusLocationsStateFlow = _getBusLocationsStateFlow.asStateFlow()

    private val _getCachedLastQueryStateFlow: MutableStateFlow<LastQueryUiModel?> =
        MutableStateFlow(null)
    val getCachedLastQueryStateFlow = _getCachedLastQueryStateFlow.asStateFlow()


    fun getBusLocations(data: String?, date: String = currentDate) =
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

    fun validateBusQueryInformation(
        departureModel: LocationDataUiModel? = currentOrigin,
        destinationModel: LocationDataUiModel? = currentDestination,
    ): Boolean {
        return if (departureModel == null || destinationModel == null) {
            false
        } else {
            validateBusQueryInfoUseCase.invoke(
                departureModel, destinationModel
            )
        }
    }

    fun cacheLastQuery(
        originName: String?,
        originId: Int?,
        destinationName: String?,
        destinationId: Int?,
        departureDateForService: String?,
        departureDateForUi: String?,
    ) = viewModelScope.launch {
        cacheLastQueryUseCase(
            originName ?: "",
            originId ?: -1,
            destinationName ?: "",
            destinationId ?: -1,
            departureDateForService ?: "",
            departureDateForUi ?: ""
        )
    }

    fun getCachedLastQuery() = viewModelScope.launch {
        getCachedLastQueryUseCase().collect { response ->
            _getCachedLastQueryStateFlow.update { response }
        }
    }


}