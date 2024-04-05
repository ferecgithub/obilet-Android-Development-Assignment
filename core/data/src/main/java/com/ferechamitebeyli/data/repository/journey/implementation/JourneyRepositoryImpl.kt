package com.ferechamitebeyli.data.repository.journey.implementation

import com.ferechamitebeyli.data.mapper.journey.JourneyDataMapper
import com.ferechamitebeyli.data.model.journey.JourneyDataUiModel
import com.ferechamitebeyli.data.repository.journey.abstraction.JourneyRepository
import com.ferechamitebeyli.network.datasource.journey.abstraction.JourneyRemoteDataSource
import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.Data
import com.ferechamitebeyli.network.util.Resource
import com.ferechamitebeyli.network.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

class JourneyRepositoryImpl @Inject constructor(
    private val remoteDataSource: JourneyRemoteDataSource,
    private val journeyDataMapper: JourneyDataMapper
) : JourneyRepository {
    override suspend fun getBusJourneys(body: GenericRequestModel<Data>): Flow<Resource<List<JourneyDataUiModel>>> {
        return safeApiCall(
            mapFromModel = {
                journeyDataMapper.mapToUiModelList(it)
            }
        ) {
            remoteDataSource.getBusJourneys(body)
        }
    }
}