package com.ferechamitebeyli.journey.domain.usecase.flightquery

import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.ui.util.UiHelpers.isArrivalDateAfterDeparture
import javax.inject.Inject

class ValidateFlightQueryInfoUseCase @Inject constructor() {
    operator fun invoke(
        departureModel: LocationDataUiModel,
        destinationModel: LocationDataUiModel,
        departureDate: String,
        arrivalDate: String
    ): Boolean {
        val areLocationsValid: Boolean = departureModel.name != destinationModel.name

        val areDatesValid: Boolean = isArrivalDateAfterDeparture(
            departureDate = departureDate,
            arrivalDate = arrivalDate
        )

        // Returns if every input is valid.
        return areLocationsValid && areDatesValid
    }
}