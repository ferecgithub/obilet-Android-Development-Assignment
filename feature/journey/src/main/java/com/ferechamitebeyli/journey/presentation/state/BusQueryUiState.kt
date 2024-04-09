package com.ferechamitebeyli.journey.presentation.state

import com.ferechamitebeyli.ui.util.UiHelpers.getFormattedDateForQuickSelection

data class BusQueryUiState(
    val departureDateForUi: String = getFormattedDateForQuickSelection(isTomorrow = true).dateForUi,
    val originName: String = "",
    val originId: Int = -1,
    val destinationName: String = "",
    val destinationId: Int = -1,
    val buttonState: Pair<BusQueryQuickSelectionButtonState, String> = Pair(
        BusQueryQuickSelectionButtonState.TOMORROW_CLICKED,
        departureDateForUi
    )
)
