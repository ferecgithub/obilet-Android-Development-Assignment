package com.ferechamitebeyli.network.dto.client.getsession.request

data class GetSessionRequestModel(
    val application: Application,
    val connection: Connection,
    val type: Int
)