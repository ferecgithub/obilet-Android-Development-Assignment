package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

data class Data(
    val available-seats: Int,
    val bus-type: String,
    val cancellation-offset: Int,
    val destination-location: String,
    val destination-location-id: Int,
    val disable-sales-without-gov-id: Boolean,
    val display-offset: Any,
    val features: List<Feature>,
    val has-bus-shuttle: Boolean,
    val id: Int,
    val is-active: Boolean,
    val is-promoted: Boolean,
    val journey: Journey,
    val origin-location: String,
    val origin-location-id: Int,
    val partner-id: Int,
    val partner-name: String,
    val partner-rating: Double,
    val route-id: Int,
    val total-seats: Int
)