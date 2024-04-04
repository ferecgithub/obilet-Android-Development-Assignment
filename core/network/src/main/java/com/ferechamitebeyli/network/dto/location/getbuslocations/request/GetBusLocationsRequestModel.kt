package com.ferechamitebeyli.network.dto.location.getbuslocations.request

import com.squareup.moshi.Json

data class GetBusLocationsRequestModel(
    val `data`: String?,
    val date: String?,
    @Json(name = "device-session")
    val deviceSession: DeviceSession?,
    val language: String?
)