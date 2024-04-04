package com.ferechamitebeyli.network.service.journey

import com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.GetBusJourneysRequestModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.response.GetBusJourneysResponseModel
import com.ferechamitebeyli.network.endpoint.journey.JourneyEndpoints.GET_BUS_JOURNEYS_ENDPOINT
import com.ferechamitebeyli.network.service.base.BaseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface JourneyService : BaseService {

    @POST(GET_BUS_JOURNEYS_ENDPOINT)
    suspend fun getBusJourneys(@Body body: GetBusJourneysRequestModel): Response<GetBusJourneysResponseModel>

}