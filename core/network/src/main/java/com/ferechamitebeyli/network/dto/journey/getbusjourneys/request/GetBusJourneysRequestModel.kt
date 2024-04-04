package com.ferechamitebeyli.network.dto.journey.getbusjourneys.request

import com.squareup.moshi.Json

data class GetBusJourneysRequestModel(
    val `data`: Data?,
    val date: String?,
    @Json(name = "device-session")
    val deviceSession: DeviceSession?,
    val language: String?
)