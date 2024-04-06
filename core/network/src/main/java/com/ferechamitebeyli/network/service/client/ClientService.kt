package com.ferechamitebeyli.network.service.client

import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import com.ferechamitebeyli.network.dto.client.getsession.response.GetSessionResponseModel
import com.ferechamitebeyli.network.endpoint.client.ClientEndpoints.GET_SESSION_ENDPOINT
import com.ferechamitebeyli.network.service.base.BaseService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface ClientService : BaseService {

    @Headers("Content-Type: application/json")
    @POST(GET_SESSION_ENDPOINT)
    suspend fun getSession(@Body body: GetSessionRequestModel): Response<GetSessionResponseModel>
}