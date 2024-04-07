package com.ferechamitebeyli.journey.presentation.state

import com.ferechamitebeyli.network.util.UiText

/**
 * Created by Ferec Hamitbeyli on 7.04.2024.
 */

sealed class JourneyResponseState<T>(
    val data: T? = null,
    val error: UiText? = null
) {
    class Success<T>(data: T? = null) : JourneyResponseState<T>(data)

    class Error<T>(text: UiText, data: T? = null) : JourneyResponseState<T>(data = data, error = text)

    class Loading<T> : JourneyResponseState<T>()

    class Idle<T> : JourneyResponseState<T>()
}