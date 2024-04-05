package com.ferechamitebeyli.network.datasource.journey.implementation

import com.ferechamitebeyli.network.datasource.journey.abstraction.JourneyRemoteDataSource
import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.dto.common.response.GenericResponseModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.response.Data
import com.ferechamitebeyli.network.service.journey.JourneyService
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

class JourneyRemoteDataSourceImpl @Inject constructor(
    private val service: JourneyService
) : JourneyRemoteDataSource {
    override suspend fun getBusJourneys(body: GenericRequestModel<com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.Data>): Response<GenericResponseModel<Data>> {
        return service.getBusJourneys(body)
    }
}
