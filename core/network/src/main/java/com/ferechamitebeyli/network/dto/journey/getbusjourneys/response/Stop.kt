package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

data class Stop(
    val is-destination: Boolean,
    val is-origin: Boolean,
    val name: String,
    val station: String,
    val time: String
)