package com.ferechamitebeyli.data.repository.client.abstraction

import com.ferechamitebeyli.data.util.Resource
import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import com.ferechamitebeyli.network.dto.client.getsession.response.GetSessionResponseModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

interface ClientRepository {
    suspend fun getSession(body: GetSessionRequestModel): Flow<Resource<GetSessionResponseModel>>
}