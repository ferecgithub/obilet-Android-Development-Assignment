package com.ferechamitebeyli.network.datasource.journey.abstraction

import com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.GetBusJourneysRequestModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.response.GetBusJourneysResponseModel
import retrofit2.Response

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface JourneyRemoteDataSource {
    suspend fun getBusJourneys(body: GetBusJourneysRequestModel): Response<GetBusJourneysResponseModel>
}