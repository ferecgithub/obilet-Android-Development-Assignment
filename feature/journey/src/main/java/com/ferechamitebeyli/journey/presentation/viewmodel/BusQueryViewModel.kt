package com.ferechamitebeyli.journey.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferechamitebeyli.data.model.journey.JourneyDataUiModel
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.domain.usecase.busquery.GetBusLocationsUseCase
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.network.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusQueryViewModel @Inject constructor(
    val getBusLocationsUseCase: GetBusLocationsUseCase
) : ViewModel() {

    var departureDateForService: String = ""
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
                        Log.d("BUSQVM", "1")
                        response.error?.let { uiText ->
                            _getBusLocationsStateFlow.update { JourneyResponseState.Error(text = uiText) }
                        }
                    }

                    is Resource.Loading -> {
                        Log.d("BUSQVM", "0")
                        _getBusLocationsStateFlow.update { JourneyResponseState.Loading() }
                    }

                    is Resource.Success -> {
                        Log.d("BUSQVM", "2")
                        response.data?.let { data ->
                            _getBusLocationsStateFlow.update { JourneyResponseState.Success(data) }
                        }
                    }
                }
            }
        }

}