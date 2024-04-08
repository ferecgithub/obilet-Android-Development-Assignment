package com.ferechamitebeyli.journey.domain.usecase.busquery

import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import javax.inject.Inject

class ValidateBusQueryInfoUseCase @Inject constructor() {
    operator fun invoke(
        departureModel: LocationDataUiModel,
        destinationModel: LocationDataUiModel
    ): Boolean {
        // Returns true if origin and destination is not the same
        return departureModel.name != destinationModel.name
    }
}