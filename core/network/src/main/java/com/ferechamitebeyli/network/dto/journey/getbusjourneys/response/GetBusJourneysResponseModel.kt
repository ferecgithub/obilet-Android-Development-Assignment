package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

data class GetBusJourneysResponseModel(
    val api-request-id: Any,
    val controller: String,
    val `data`: List<Data>,
    val message: Any,
    val status: String,
    val user-message: Any
)