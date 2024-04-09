package com.ferechamitebeyli.journey.domain.usecase.common

import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import javax.inject.Inject

class CacheLastQueriedDestinationUseCase @Inject constructor(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(
        destinationName: String,
        destinationId: Int
    ) = repository.cacheLastQueriedDestination(
        destinationName,
        destinationId
    )
}