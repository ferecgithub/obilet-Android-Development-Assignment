package com.ferechamitebeyli.network.datasource.location.implementation

import com.ferechamitebeyli.network.datasource.location.abstraction.LocationRemoteDataSource
import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.dto.common.response.GenericResponseModel
import com.ferechamitebeyli.network.dto.location.getbuslocations.response.Data
import com.ferechamitebeyli.network.service.location.LocationService
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

class LocationRemoteDataSourceImpl @Inject constructor(
    private val service: LocationService
) : LocationRemoteDataSource {
    override suspend fun getBusLocations(body: GenericRequestModel<String>): Response<GenericResponseModel<Data>> {
        return service.getBusLocations(body)
    }
}