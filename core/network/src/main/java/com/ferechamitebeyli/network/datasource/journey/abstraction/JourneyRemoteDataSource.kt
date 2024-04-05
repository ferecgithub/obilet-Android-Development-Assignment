package com.ferechamitebeyli.network.datasource.journey.abstraction

import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.dto.common.response.GenericResponseModel
import com.ferechamitebeyli.network.dto.journey.getbusjourneys.response.Data
import retrofit2.Response

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface JourneyRemoteDataSource {
    suspend fun getBusJourneys(body: GenericRequestModel<com.ferechamitebeyli.network.dto.journey.getbusjourneys.request.Data>): Response<GenericResponseModel<Data>>
}