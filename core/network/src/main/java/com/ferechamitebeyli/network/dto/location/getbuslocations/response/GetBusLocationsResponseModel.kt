package com.ferechamitebeyli.network.dto.location.getbuslocations.response

import com.squareup.moshi.Json

data class GetBusLocationsResponseModel(
    @Json(name = "api-request-id")
    val apiRequestId: Any?,
    @Json(name = "client-request-id")
    val clientRequestId: Any?,
    val controller: String,
    @Json(name = "correlation-id")
    val correlationId: String,
    val `data`: List<Data>,
    val message: Any?,
    val parameters: Any?,
    val status: String,
    @Json(name = "user-message")
    val userMessage: Any?,
    @Json(name = "web-correlation-id")
    val webCorrelationId: Any?
)