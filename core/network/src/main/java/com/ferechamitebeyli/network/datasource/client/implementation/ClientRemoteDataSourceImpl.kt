package com.ferechamitebeyli.network.datasource.client.implementation

import com.ferechamitebeyli.network.datasource.client.abstraction.ClientRemoteDataSource
import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import com.ferechamitebeyli.network.dto.client.getsession.response.GetSessionResponseModel
import com.ferechamitebeyli.network.service.client.ClientService
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

class ClientRemoteDataSourceImpl @Inject constructor(
    private val service: ClientService
) : ClientRemoteDataSource {
    override suspend fun getSession(body: GetSessionRequestModel): Response<GetSessionResponseModel> {
        return service.getSession(body)
    }
}