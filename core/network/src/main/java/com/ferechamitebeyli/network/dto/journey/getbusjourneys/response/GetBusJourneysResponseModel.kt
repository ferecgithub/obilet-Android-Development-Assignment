package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

import com.squareup.moshi.Json

data class GetBusJourneysResponseModel(
    @Json(name = "api-request-id")
    val apiRequestId: Any?,
    val controller: String,
    val `data`: List<Data>,
    val message: Any?,
    val status: String,
    @Json(name = "user-message")
    val userMessage: Any?
)