package com.ferechamitebeyli.data.repository.client.implementation

import com.ferechamitebeyli.caching.session.abstraction.SessionCachingManager
import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import com.ferechamitebeyli.data.util.Resource
import com.ferechamitebeyli.network.datasource.client.abstraction.ClientRemoteDataSource
import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import com.ferechamitebeyli.network.dto.client.getsession.response.GetSessionResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

class ClientRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClientRemoteDataSource,
    private val cachingDataSource: SessionCachingManager
): ClientRepository{
    override suspend fun getSession(body: GetSessionRequestModel): Flow<Resource<GetSessionResponseModel>> {
        return flow {
            emit(Resource.Loading())
        }
    }
}