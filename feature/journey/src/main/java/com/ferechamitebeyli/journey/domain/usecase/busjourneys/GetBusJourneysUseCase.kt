package com.ferechamitebeyli.journey.domain.usecase.busjourneys

import com.ferechamitebeyli.data.model.journey.JourneyDataUiModel
import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import com.ferechamitebeyli.data.repository.journey.abstraction.JourneyRepository
import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.Data
import com.ferechamitebeyli.network.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetBusJourneysUseCase @Inject constructor(
    private val repository: JourneyRepository,
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(
        data: Data?,
        date: String,
        language: String = "tr-TR"
    ): Flow<Resource<List<JourneyDataUiModel>>> {
        val model = GenericRequestModel(
            data = data,
            deviceSession = clientRepository.getCachedDeviceSession().first(),
            date = date,
            language = language
        )
        return repository.getBusJourneys(model)
    }
}