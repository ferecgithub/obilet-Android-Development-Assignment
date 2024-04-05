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
) {
    inline fun <U> mapToUiModel(mapDtoToUiModel: (T) -> U): GenericResponseModel<U> {
        return GenericResponseModel(
            status = this.status,
            data = this.data?.let {
                it.map { element ->
                    mapDtoToUiModel(element)
                }
            },
            message = this.message,
            userMessage = this.userMessage,
            apiRequestId = this.apiRequestId,
            controller = this.controller
        )
    }
}

