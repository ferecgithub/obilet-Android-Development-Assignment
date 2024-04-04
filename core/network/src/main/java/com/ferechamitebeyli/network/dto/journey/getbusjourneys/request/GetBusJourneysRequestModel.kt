package com.ferechamitebeyli.network.dto.journey.getbusjourneys.request

data class GetBusJourneysRequestModel(
    val `data`: Data,
    val date: String,
    val device-session: DeviceSession,
    val language: String
)