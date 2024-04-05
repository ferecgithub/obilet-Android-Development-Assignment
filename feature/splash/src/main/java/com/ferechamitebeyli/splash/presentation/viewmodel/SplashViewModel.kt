package com.ferechamitebeyli.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import com.ferechamitebeyli.network.dto.client.getsession.response.GetSessionResponseModel
import com.ferechamitebeyli.network.util.Resource
import com.ferechamitebeyli.splash.domain.usecase.GetSessionUseCase
import com.ferechamitebeyli.splash.presentation.state.SplashResponseState
import com.ferechamitebeyli.splash.presentation.state.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    val getSessionUseCase: GetSessionUseCase,
) : ViewModel() {

    val channel = Channel<SplashScreenState>()

    private val _getSessionStateFlow: MutableStateFlow<SplashResponseState<GetSessionResponseModel>> =
        MutableStateFlow(
            SplashResponseState.Idle()
        )
    val getSessionStateFlow = _getSessionStateFlow.asStateFlow()


    fun getSession(body: GetSessionRequestModel) = viewModelScope.launch {
        getSessionUseCase(body).collect { response ->
            when (response) {
                is Resource.Error -> {
                    response.error?.let { uiText ->
                        _getSessionStateFlow.update { SplashResponseState.Error(text = uiText) }
                    }
                }

                is Resource.Loading -> {
                    _getSessionStateFlow.update { SplashResponseState.Loading() }
                }

                is Resource.Success -> {
                    response.data?.let { data ->
                        _getSessionStateFlow.update { SplashResponseState.Success(data) }
                    }
                }
            }
        }
    }
}