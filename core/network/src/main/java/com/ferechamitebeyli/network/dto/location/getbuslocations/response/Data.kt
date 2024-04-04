package com.ferechamitebeyli.network.dto.location.getbuslocations.response

import com.squareup.moshi.Json

data class Data(
    @Json(name = "area-code")
    val areaCode: Any?,
    @Json(name = "city-id")
    val cityId: Int?,
    @Json(name = "city-name")
    val cityName: String?,
    val code: Any?,
    @Json(name = "country-id")
    val countryId: Int,
    @Json(name = "country-name")
    val countryName: String,
    @Json(name = "geo-location")
    val geoLocation: GeoLocation,
    val id: Int,
    @Json(name = "is-city-center")
    val isCityCenter: Boolean,
    val keywords: String,
    val languages: Any?,
    @Json(name = "long-name")
    val longName: String,
    val name: String,
    @Json(name = "parent-id")
    val parentId: Int?,
    val rank: Int?,
    @Json(name = "reference-code")
    val referenceCode: String,
    @Json(name = "reference-country")
    val referenceCountry: String?,
    @Json(name = "show-country")
    val showCountry: Boolean,
    val type: String,
    @Json(name = "tz-code")
    val tzCode: String,
    @Json(name = "weather-code")
    val weatherCode: String,
    val zoom: Int
)