package com.ferechamitebeyli.splash.presentation.state

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

sealed class SplashScreenState {
    data object SessionIsEstablished: SplashScreenState()
}