package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

data class Journey(
    val arrival: String,
    val available: Any,
    val booking: Any,
    val bus-name: String,
    val code: String,
    val currency: String,
    val departure: String,
    val description: Any,
    val destination: String,
    val duration: String,
    val features: List<String>,
    val internet-price: Int,
    val kind: String,
    val origin: String,
    val original-price: Int,
    val policy: Policy,
    val stops: List<Stop>
)