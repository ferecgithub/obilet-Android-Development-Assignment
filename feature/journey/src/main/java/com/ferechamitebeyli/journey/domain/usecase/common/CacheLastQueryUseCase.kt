package com.ferechamitebeyli.journey.domain.usecase.common

import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import javax.inject.Inject

class CacheLastQueryUseCase @Inject constructor(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(
        originName: String,
        originId: Int,
        destinationName: String,
        destinationId: Int,
        departureDateForService: String,
        departureDateForUi: String,
    ) = repository.cacheLastQuery(
        originName,
        originId,
        destinationName,
        destinationId,
        departureDateForService,
        departureDateForUi
    )
}