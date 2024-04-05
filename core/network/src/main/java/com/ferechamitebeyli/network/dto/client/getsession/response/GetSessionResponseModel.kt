package com.ferechamitebeyli.network.dto.client.getsession.response

import com.squareup.moshi.Json

data class GetSessionResponseModel(
    val status: String?,
    val `data`: Data?,
    val message: String?,
    @Json(name = "user-message")
    val userMessage: String?,
    @Json(name = "api-request-id")
    val apiRequestId: Any?,
    val controller: String?,
)