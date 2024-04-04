package com.ferechamitebeyli.network.dto.journey.getbusjourneys.response

data class Policy(
    val gov-id: Boolean,
    val lht: Boolean,
    val max-seats: Any,
    val max-single: Any,
    val max-single-females: Any,
    val max-single-males: Any,
    val mixed-genders: Boolean
)