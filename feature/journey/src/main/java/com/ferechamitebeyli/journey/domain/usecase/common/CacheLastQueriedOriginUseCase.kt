package com.ferechamitebeyli.journey.domain.usecase.common

import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import javax.inject.Inject

class CacheLastQueriedOriginUseCase @Inject constructor(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(
        originName: String,
        originId: Int,
    ) = repository.cacheLastQueriedOrigin(
        originName,
        originId
    )
}