package com.ferechamitebeyli.network.dto.location.getbuslocations.response

data class Data(
    val area-code: Any,
    val city-id: Int,
    val city-name: String,
    val code: Any,
    val country-id: Int,
    val country-name: String,
    val geo-location: GeoLocation,
    val id: Int,
    val is-city-center: Boolean,
    val keywords: String,
    val languages: Any,
    val long-name: String,
    val name: String,
    val parent-id: Int,
    val rank: Int,
    val reference-code: String,
    val reference-country: Any,
    val show-country: Boolean,
    val type: String,
    val tz-code: String,
    val weather-code: Any,
    val zoom: Int
)