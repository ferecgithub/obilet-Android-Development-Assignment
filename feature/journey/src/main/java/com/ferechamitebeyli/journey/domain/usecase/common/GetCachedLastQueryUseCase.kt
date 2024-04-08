package com.ferechamitebeyli.journey.domain.usecase.common

import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import javax.inject.Inject

class GetCachedLastQueryUseCase @Inject constructor(
    private val repository: ClientRepository
) {
    operator fun invoke() = repository.getCachedLastQueriedInformation()
}