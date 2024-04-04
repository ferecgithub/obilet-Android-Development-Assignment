package com.ferechamitebeyli.network.dto.client.getsession.request

import com.squareup.moshi.Json

data class Application(
    @Json(name = "equipment-id")
    val equipmentId: String,
    @Json(name = "version")
    val version: String
)