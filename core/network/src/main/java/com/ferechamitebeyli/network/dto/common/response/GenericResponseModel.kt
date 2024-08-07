package com.ferechamitebeyli.network.dto.common.response

import com.squareup.moshi.Json

data class GenericResponseModel<T>(
    val status: String?,
    val `data`: List<T>?,
    val message: String?,
    @Json(name = "user-message")
    val userMessage: String?,
    @Json(name = "api-request-id")
    val apiRequestId: Any?,
    val controller: String?,
)

