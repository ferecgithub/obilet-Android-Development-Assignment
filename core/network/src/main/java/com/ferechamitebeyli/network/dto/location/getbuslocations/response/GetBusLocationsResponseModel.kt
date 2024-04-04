package com.ferechamitebeyli.network.dto.location.getbuslocations.response

data class GetBusLocationsResponseModel(
    val api-request-id: Any,
    val client-request-id: Any,
    val controller: String,
    val correlation-id: String,
    val `data`: List<Data>,
    val message: Any,
    val parameters: Any,
    val status: String,
    val user-message: Any,
    val web-correlation-id: Any
)