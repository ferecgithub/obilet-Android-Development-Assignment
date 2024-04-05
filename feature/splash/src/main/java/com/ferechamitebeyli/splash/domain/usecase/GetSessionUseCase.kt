package com.ferechamitebeyli.splash.domain.usecase

import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

class GetSessionUseCase @Inject constructor(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(body: GetSessionRequestModel) = repository.getSession(body)
}