package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

import com.squareup.moshi.Json

data class Feature(
    @Json(name = "back-color")
    val backColor: Any?,
    val description: String?,
    @Json(name = "fore-color")
    val foreColor: Any?,
    val id: Int?,
    @Json(name = "is-promoted")
    val isPromoted: Boolean?,
    val name: String?,
    val priority: Int?
)