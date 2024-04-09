package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

import com.squareup.moshi.Json

data class Stop(
    val id: Int?,
    val index: Int?,
    @Json(name = "is-destination")
    val isDestination: Boolean?,
    @Json(name = "is-origin")
    val isOrigin: Boolean?,
    @Json(name = "is-segment-stop")
    val isSegmentstop: Boolean?,
    val kolayCarLocationId: Int?,
    val name: String?,
    @Json(name = "obilet-station-id")
    val obiletstationId: Any?,
    val station: String?,
    val time: String?
)