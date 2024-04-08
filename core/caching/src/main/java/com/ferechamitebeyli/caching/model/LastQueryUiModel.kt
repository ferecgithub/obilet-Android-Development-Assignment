package com.ferechamitebeyli.caching.model

data class LastQueryUiModel(
    val originName: String?,
    val originId: Int?,
    val destinationName: String?,
    val destinationId: Int?,
    val departureDateForService: String?,
    val departureDateForUi: String?,
)