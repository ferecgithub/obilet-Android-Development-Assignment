package com.ferechamitebeyli.data.model.location

import com.ferechamitebeyli.network.dto.location.getbuslocations.response.GeoLocation

data class LocationDataUiModel(
    val id: Int,
    val parentId: Int?,
    val type: String?,
    val name: String?,
    val geoLocation: GeoLocation,
    val tzCode: String,
    val weatherCode: String?,
    val rank: Int?,
    val referenceCode: String?,
    val keywords: String?,
    val areaCode: Any?,
    val cityId: Int?,
    val cityName: String?,
    val code: Any?,
    val countryId: Int?,
    val countryName: String?,
)