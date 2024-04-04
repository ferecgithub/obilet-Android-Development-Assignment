package com.ferechamitebeyli.network.dto.client.getsession.response

import com.squareup.moshi.Json

data class Data(
    val affiliate: Any?,
    val device: Any?,
    @Json(name = "device-id")
    val deviceId: String?,
    @Json(name = "device-type")
    val deviceType: Int?,
    @Json(name = "ip-country")
    val ipCountry: String?,
    @Json(name = "session-id")
    val sessionId: String?
)