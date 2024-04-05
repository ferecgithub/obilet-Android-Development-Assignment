package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

import com.squareup.moshi.Json

data class Data(
    @Json(name = "available-seats")
    val availableSeats: Int?,
    @Json(name = "bus-type")
    val busType: String?,
    @Json(name = "cancellation-offset")
    val cancellationOffset: Int?,
    @Json(name = "destination-location")
    val destinationLocation: String?,
    @Json(name = "destination-location-id")
    val destinationLocationId: Int?,
    @Json(name = "disable-sales-without-gov-id")
    val disableSalesWithoutGovId: Boolean?,
    @Json(name = "display-offset")
    val displayOffset: Any?,
    val features: List<Feature>?,
    @Json(name = "has-bus-shuttle")
    val hasBusShuttle: Boolean?,
    val id: Int?,
    @Json(name = "is-active")
    val isActive: Boolean?,
    @Json(name = "is-promoted")
    val isPromoted: Boolean?,
    val journey: Journey?,
    @Json(name = "origin-location")
    val originLocation: String?,
    @Json(name = "origin-location-id")
    val originLocationId: Int?,
    @Json(name = "partner-id")
    val partnerId: Int?,
    @Json(name = "partner-name")
    val partnerName: String?,
    @Json(name = "partner-rating")
    val partnerRating: Double?,
    @Json(name = "route-id")
    val routeId: Int?,
    @Json(name = "total-seats")
    val totalSeats: Int?
)