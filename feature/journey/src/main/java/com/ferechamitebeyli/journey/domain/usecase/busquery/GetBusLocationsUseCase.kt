package com.ferechamitebeyli.journey.domain.usecase.busquery

import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import com.ferechamitebeyli.data.repository.location.abstraction.LocationRepository
import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetBusLocationsUseCase @Inject constructor(
    private val repository: LocationRepository,
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(
        data: String?,
        date: String,
        language: String = "tr-TR"
    ): Flow<Resource<List<LocationDataUiModel>>> {
        val model = GenericRequestModel(
            data = data,
            deviceSession = clientRepository.getCachedDeviceSession().first(),
            date = date,
            language = language
        )
        return repository.getBusLocations(model)
    }
}