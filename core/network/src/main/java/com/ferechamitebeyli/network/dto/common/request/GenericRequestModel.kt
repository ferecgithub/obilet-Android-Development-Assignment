package com.ferechamitebeyli.network.dto.common.request

import com.squareup.moshi.Json

data class GenericRequestModel<T>(
    val `data`: T?,
    @Json(name = "device-session")
    val deviceSession: DeviceSession?,
    val date: String?,
    val language: String?
)