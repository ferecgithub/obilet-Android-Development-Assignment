package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

import com.squareup.moshi.Json

data class Stop(
    @Json(name = "is-destination")
    val isDestination: Boolean,
    @Json(name = "is-origin")
    val isOrigin: Boolean,
    val name: String,
    val station: String,
    val time: String
)