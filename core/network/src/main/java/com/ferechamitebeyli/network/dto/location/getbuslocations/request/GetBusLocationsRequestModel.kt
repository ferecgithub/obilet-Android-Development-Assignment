package com.ferechamitebeyli.network.dto.location.getbuslocations.request

data class GetBusLocationsRequestModel(
    val `data`: Any,
    val date: String,
    val device-session: DeviceSession,
    val language: String
)