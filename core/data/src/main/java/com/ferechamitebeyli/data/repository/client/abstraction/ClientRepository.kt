package com.ferechamitebeyli.data.repository.client.abstraction

import com.ferechamitebeyli.data.model.common.LastQueryUiModel
import com.ferechamitebeyli.network.util.Resource
import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import com.ferechamitebeyli.network.dto.client.getsession.response.GetSessionResponseModel
import com.ferechamitebeyli.network.dto.common.request.DeviceSession
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

interface ClientRepository {
    suspend fun getSession(body: GetSessionRequestModel): Flow<Resource<GetSessionResponseModel>>

    fun getCachedDeviceSession(): Flow<DeviceSession>
    fun getCachedLastQueriedInformation(): Flow<LastQueryUiModel>
}