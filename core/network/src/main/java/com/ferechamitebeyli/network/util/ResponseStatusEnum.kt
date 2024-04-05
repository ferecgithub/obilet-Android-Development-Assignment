package com.ferechamitebeyli.network.util

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

enum class ResponseStatusEnum(val status: String) {
    SUCCESS("Success"),
    DEVICE_SESSION_ERROR("DeviceSessionError"),
    INVALID_DEPARTURE_DATE("InvalidDepartureDate"),
}