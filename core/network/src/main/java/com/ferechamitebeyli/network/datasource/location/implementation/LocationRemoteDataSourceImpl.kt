package com.ferechamitebeyli.network.datasource.location.implementation

import com.ferechamitebeyli.network.datasource.location.abstraction.LocationRemoteDataSource
import com.ferechamitebeyli.network.dto.location.getbuslocations.request.GetBusLocationsRequestModel
import com.ferechamitebeyli.network.dto.location.getbuslocations.response.GetBusLocationsResponseModel
import com.ferechamitebeyli.network.service.location.LocationService
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

class LocationRemoteDataSourceImpl @Inject constructor(
    private val service: LocationService
) : LocationRemoteDataSource {
    override suspend fun getBusLocations(body: GetBusLocationsRequestModel): Response<GetBusLocationsResponseModel> {
        return service.getBusLocations(body)
    }
}