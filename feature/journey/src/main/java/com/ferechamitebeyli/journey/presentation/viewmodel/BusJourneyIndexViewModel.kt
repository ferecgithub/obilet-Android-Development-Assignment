package com.ferechamitebeyli.journey.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferechamitebeyli.data.model.journey.JourneyDataUiModel
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.domain.usecase.busjourneys.GetBusJourneysUseCase
import com.ferechamitebeyli.journey.domain.usecase.busquery.GetBusLocationsUseCase
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.Data
import com.ferechamitebeyli.network.util.Resource
import com.ferechamitebeyli.ui.util.UiHelpers.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusJourneyIndexViewModel @Inject constructor(
    private val getBusJourneysUseCase: GetBusJourneysUseCase
) : ViewModel() {

    private val currentDate = getCurrentDateTime()

    private val _getBusJourneysStateFlow: MutableStateFlow<JourneyResponseState<List<JourneyDataUiModel>>> =
        MutableStateFlow(
            JourneyResponseState.Idle()
        )
    val getBusJourneysStateFlow = _getBusJourneysStateFlow.asStateFlow()


    fun getBusJourneys(data: Data?, date: String = currentDate) =
        viewModelScope.launch {
            getBusJourneysUseCase(data, date).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        response.error?.let { uiText ->
                            _getBusJourneysStateFlow.update { JourneyResponseState.Error(text = uiText) }
                        }
                    }

                    is Resource.Loading -> {
                        _getBusJourneysStateFlow.update { JourneyResponseState.Loading() }
                    }

                    is Resource.Success -> {
                        response.data?.let { data ->
                            _getBusJourneysStateFlow.update { JourneyResponseState.Success(data) }
                        }
                    }
                }
            }
        }

}