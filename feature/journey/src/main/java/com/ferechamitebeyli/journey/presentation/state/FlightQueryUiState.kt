package com.ferechamitebeyli.journey.presentation.state

import com.ferechamitebeyli.ui.util.UiHelpers.getFormattedDateForArrivalDateAccordingToDepartureDate
import com.ferechamitebeyli.ui.util.UiHelpers.getFormattedDateForQuickSelection

data class FlightQueryUiState(
    val departureDateForUi: String = getFormattedDateForQuickSelection(isTomorrow = true).dateForUi,
    val arrivalDateForUi: String = getFormattedDateForArrivalDateAccordingToDepartureDate(
        departureDate = departureDateForUi
    ).dateForUi,
    val originName: String = "",
    val originId: Int = -1,
    val destinationName: String = "",
    val destinationId: Int = -1
)
