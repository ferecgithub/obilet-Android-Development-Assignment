package com.ferechamitebeyli.data.repository.client.abstraction

import com.ferechamitebeyli.caching.model.LastQueryUiModel
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

    suspend fun cacheLastQueriedOrigin(
        originName: String,
        originId: Int,
    )

    suspend fun cacheLastQueriedDestination(
        destinationName: String,
        destinationId: Int,
    )

    suspend fun cacheLastQueriedDepartureDate(
        departureDateForService: String,
        departureDateForUi: String,
    )

    suspend fun cacheDeviceSession(deviceId: String, sessionId: String)

    suspend fun clearCache()
}