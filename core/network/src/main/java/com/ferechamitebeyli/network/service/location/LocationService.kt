package com.ferechamitebeyli.network.service.location

import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.dto.common.response.GenericResponseModel
import com.ferechamitebeyli.network.dto.location.getbuslocations.response.Data
import com.ferechamitebeyli.network.endpoint.location.LocationEndpoints.GET_BUS_LOCATIONS_ENDPOINT
import com.ferechamitebeyli.network.service.base.BaseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface LocationService : BaseService {

    @POST(GET_BUS_LOCATIONS_ENDPOINT)
    suspend fun getBusLocations(@Body body: GenericRequestModel<String>): Response<GenericResponseModel<Data>>
}