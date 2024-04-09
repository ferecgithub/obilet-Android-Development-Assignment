package com.ferechamitebeyli.journey.domain.usecase.common

import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import javax.inject.Inject

class CacheLastQueriedDepartureDateUseCase @Inject constructor(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(
        departureDateForService: String,
        departureDateForUi: String,
    ) = repository.cacheLastQueriedDepartureDate(
        departureDateForService,
        departureDateForUi
    )
}