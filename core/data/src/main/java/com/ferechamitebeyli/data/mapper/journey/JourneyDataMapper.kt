package com.ferechamitebeyli.data.mapper.journey

import com.ferechamitebeyli.data.mapper.base.BaseMapper
import com.ferechamitebeyli.data.model.journey.JourneyDataUiModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.response.Data
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

class JourneyDataMapper @Inject constructor() : BaseMapper<Data, JourneyDataUiModel> {
    override fun mapToUiModel(model: Data): JourneyDataUiModel {
        return JourneyDataUiModel(
            id = model.id,
            availableSeats = model.availableSeats,
            busType = model.busType,
            cancellationOffset = model.cancellationOffset,
            destinationLocation = model.destinationLocation,
            destinationLocationId = model.destinationLocationId,
            disableSalesWithoutGovId = model.disableSalesWithoutGovId,
            displayOffset = model.displayOffset,
            features = model.features,
            hasBusShuttle = model.hasBusShuttle,
            isActive = model.isActive,
            isPromoted = model.isPromoted,
            journey = model.journey,
            originLocation = model.originLocation,
            originLocationId = model.originLocationId,
            partnerId = model.partnerId,
            partnerName = model.partnerName,
            partnerRating = model.partnerRating,
            routeId = model.routeId,
            totalSeats = model.totalSeats
        )
    }

}