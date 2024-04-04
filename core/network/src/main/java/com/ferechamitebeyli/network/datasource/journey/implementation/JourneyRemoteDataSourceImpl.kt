package com.ferechamitebeyli.network.datasource.journey.implementation

import com.ferechamitebeyli.network.datasource.journey.abstraction.JourneyRemoteDataSource
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.GetBusJourneysRequestModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.response.GetBusJourneysResponseModel
import com.ferechamitebeyli.network.service.journey.JourneyService
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

class JourneyRemoteDataSourceImpl @Inject constructor(
    private val service: JourneyService
) : JourneyRemoteDataSource {
    override suspend fun getBusJourneys(body: GetBusJourneysRequestModel): Response<GetBusJourneysResponseModel> {
        return service.getBusJourneys(body)
    }
}
