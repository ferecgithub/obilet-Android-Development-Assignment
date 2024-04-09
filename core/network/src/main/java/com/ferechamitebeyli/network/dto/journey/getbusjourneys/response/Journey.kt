package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

import com.squareup.moshi.Json

data class Journey(
    val arrival: String?,
    val available: Any?,
    val booking: Any?,
    @Json(name = "bus-name")
    val busName: String?,
    val code: String?,
    val currency: String?,
    val departure: String?,
    val description: String?,
    val destination: String?,
    val duration: String?,
    val features: List<String> = emptyList(),
    @Json(name = "internet-price")
    val internetPrice: Int?,
    val kind: String?,
    val origin: String?,
    @Json(name = "original-price")
    val originalPrice: Int?,
    val policy: Policy?,
    val stops: List<Stop>?
)