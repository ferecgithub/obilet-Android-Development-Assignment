package com.ferechamitebeyli.network.dto.client.getsession.request

import com.squareup.moshi.Json

data class Connection(
    @Json(name = "ip-address")
    val ipAddress: String?
)