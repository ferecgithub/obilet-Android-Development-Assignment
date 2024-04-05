package com.ferechamitebeyli.splash.domain.usecase

import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

class CacheDeviceSessionUseCase @Inject constructor(
    private val repository: ClientRepository
) {
    operator fun invoke() = repository.getCachedDeviceSession()
}