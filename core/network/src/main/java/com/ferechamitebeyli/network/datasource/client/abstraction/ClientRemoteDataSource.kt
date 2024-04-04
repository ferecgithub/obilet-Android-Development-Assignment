package com.ferechamitebeyli.network.datasource.client.abstraction

import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import com.ferechamitebeyli.network.dto.client.getsession.response.GetSessionResponseModel
import retrofit2.Response

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface ClientRemoteDataSource {

    suspend fun getSession(body: GetSessionRequestModel): Response<GetSessionResponseModel>

}