package com.ferechamitebeyli.network.datasource.location.abstraction

import com.ferechamitebeyli.network.dto.location.getbuslocations.request.GetBusLocationsRequestModel
import com.ferechamitebeyli.network.dto.location.getbuslocations.response.GetBusLocationsResponseModel
import retrofit2.Response

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface LocationRemoteDataSource {
    suspend fun getBusLocations(body: GetBusLocationsRequestModel): Response<GetBusLocationsResponseModel>
}