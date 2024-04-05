package com.ferechamitebeyli.network.util

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

sealed class Resource<T>(
    val data: T? = null,
    val error: UiText? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)

    class Error<T>(data: T? = null, text: UiText) : Resource<T>(data = data, error = text)

    class Loading<T>(data: T? = null) : Resource<T>(data)

}