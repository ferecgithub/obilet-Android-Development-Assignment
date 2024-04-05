package com.ferechamitebeyli.network.datasource.location.abstraction

import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.dto.common.response.GenericResponseModel
import com.ferechamitebeyli.network.dto.location.getbuslocations.response.Data
import retrofit2.Response

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface LocationRemoteDataSource {
    suspend fun getBusLocations(body: GenericRequestModel<String>): Response<GenericResponseModel<Data>>
}