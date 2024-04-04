package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

import com.squareup.moshi.Json

data class Policy(
    @Json(name = "gov-id")
    val govId: Boolean,
    val lht: Boolean,
    @Json(name = "max-seats")
    val maxSeats: Int?,
    @Json(name = "max-single")
    val maxSingle: Int?,
    @Json(name = "max-single-females")
    val maxSingleFemales: Int?,
    @Json(name = "max-single-males")
    val maxSingleMales: Int?,
    @Json(name = "mixed-genders")
    val mixedGenders: Boolean
)