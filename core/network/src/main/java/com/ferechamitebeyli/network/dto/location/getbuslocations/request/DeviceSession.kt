package com.ferechamitebeyli.network.dto.location.getbuslocations.request

import com.squareup.moshi.Json

data class DeviceSession(
    @Json(name = "device-id")
    val deviceId: String?,
    @Json(name = "session-id")
    val sessionId: String?
)