package com.ferechamitebeyli.splash.presentation.state

import com.ferechamitebeyli.network.util.UiText

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

sealed class SplashResponseState<T>(
    val data: T? = null,
    val error: UiText? = null
) {
    class Success<T>(data: T? = null) : SplashResponseState<T>(data)

    class Error<T>(text: UiText, data: T? = null) : SplashResponseState<T>(data = data, error = text)

    class Loading<T> : SplashResponseState<T>()

    class Idle<T> : SplashResponseState<T>()
}