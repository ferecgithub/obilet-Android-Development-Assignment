package com.ferechamitebeyli.data.repository.journey.abstraction

import com.ferechamitebeyli.data.model.journey.JourneyDataUiModel
import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.Data
import com.ferechamitebeyli.network.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

interface JourneyRepository {
    suspend fun getBusJourneys(body: GenericRequestModel<Data>): Flow<Resource<List<JourneyDataUiModel>>>
}