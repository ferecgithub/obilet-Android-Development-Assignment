package com.ferechamitebeyli.data.model.journey

import com.ferechamitebeyli.network.dto.journey.getbusjourneys.response.Feature
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.response.Journey

data class JourneyDataUiModel(
    val availableSeats: Int?,
    val busType: String?,
    val cancellationOffset: Int?,
    val destinationLocation: String?,
    val destinationLocationId: Int?,
    val disableSalesWithoutGovId: Boolean?,
    val displayOffset: Any?,
    val features: List<Feature>?,
    val hasBusShuttle: Boolean?,
    val id: Int?,
    val isActive: Boolean?,
    val isPromoted: Boolean?,
    val journey: Journey?,
    val originLocation: String?,
    val originLocationId: Int?,
    val partnerId: Int?,
    val partnerName: String?,
    val partnerRating: Double?,
    val routeId: Int?,
    val totalSeats: Int?
)