package com.ferechamitebeyli.network.dto.journey.getbusjourneys.request

import com.squareup.moshi.Json

data class Data(
    @Json(name = "departure-date")
    val departureDate: String?,
    @Json(name = "destination-id")
    val destinationId: Int?,
    @Json(name = "origin-id")
    val originId: Int?
)