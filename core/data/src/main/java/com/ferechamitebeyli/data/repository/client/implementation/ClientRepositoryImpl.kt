package com.ferechamitebeyli.data.repository.client.implementation

import com.ferechamitebeyli.caching.session.abstraction.SessionCachingManager
import com.ferechamitebeyli.data.model.common.LastQueryUiModel
import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import com.ferechamitebeyli.network.util.Resource
import com.ferechamitebeyli.network.datasource.client.abstraction.ClientRemoteDataSource
import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import com.ferechamitebeyli.network.dto.client.getsession.response.GetSessionResponseModel
import com.ferechamitebeyli.network.dto.common.request.DeviceSession
import com.ferechamitebeyli.network.util.ResponseStatusEnum
import com.ferechamitebeyli.network.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

class ClientRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClientRemoteDataSource,
    private val cachingDataSource: SessionCachingManager
) : ClientRepository {
    override suspend fun getSession(body: GetSessionRequestModel): Flow<Resource<GetSessionResponseModel>> {
        return flow {
            try {
                emit(Resource.Loading())
                //fetch the response from the api within the given coroutine context.
                val response = withContext(Dispatchers.IO) { remoteDataSource.getSession(body) }
                if (response.isSuccessful) {
                    /* if the response is success
                     * which means response.code is in between 200-300
                     * we are checking if the data is null or not
                     * if not null, we are mapping it out and return
                     * if null, we are return error with the corresponding message.
                     */
                    response.body()?.let { model ->
                        if (model.status == ResponseStatusEnum.SUCCESS.status) {
                            model.data?.let {
                                // Caching the device session
                                cachingDataSource.cacheDeviceSession(
                                    model.data?.deviceId ?: "",
                                    model.data?.sessionId ?: ""
                                )
                                emit(Resource.Success(model))
                            }
                        } else {
                            emit(
                                Resource.Error(
                                    text = UiText.DynamicString(model.userMessage)
                                )
                            )
                        }

                    } ?: emit(
                        Resource.Error(
                            text = UiText.StringResource(com.ferechamitebeyli.network.R.string.message_safeApiCall_noResult)
                        )
                    )
                } else {
                    emit(
                        Resource.Error(text = UiText.StringResource(com.ferechamitebeyli.network.R.string.message_safeApiCall_operationFailed))
                    )

                }
            } catch (exception: Exception) {
                //handling exceptions
                when (exception) {
                    is TimeoutCancellationException -> {
                        emit(Resource.Error(text = UiText.StringResource(com.ferechamitebeyli.network.R.string.message_safeApiCall_timeoutError)))
                    }

                    is IOException -> {
                        emit(
                            Resource.Error(
                                text = exception.localizedMessage?.let { message ->
                                    UiText.DynamicString(message)
                                }
                                    ?: UiText.StringResource(com.ferechamitebeyli.network.R.string.message_safeApiCall_operationFailed)
                            ))
                    }

                    else -> {
                        emit(Resource.Error(text = UiText.StringResource(com.ferechamitebeyli.network.R.string.message_safeApiCall_unknownError)))
                    }
                }
            }
        }
    }

    override fun getCachedDeviceSession(): Flow<DeviceSession> {
        return cachingDataSource.getCachedDeviceId()
            .zip(cachingDataSource.getCachedSessionId()) { deviceId, sessionId ->
                DeviceSession(
                    deviceId = deviceId,
                    sessionId = sessionId
                )
            }
    }

    override fun getCachedLastQueriedInformation(): Flow<LastQueryUiModel> {
        return combine(
            cachingDataSource.getLastQueriedOrigin(),
            cachingDataSource.getLastQueriedDestination(),
            cachingDataSource.getLastQueriedDepartureDate()
        ) { origin, destination, departureDate ->
            LastQueryUiModel(
                origin = origin,
                destination = destination,
                departureDate = departureDate
            )
        }
    }

    override suspend fun cacheLastQueries(
        origin: String,
        destination: String,
        departureDate: String
    ) {
        cachingDataSource.cacheLastQueries(
            origin, destination, departureDate
        )
    }

    override suspend fun cacheDeviceSession(deviceId: String, sessionId: String) {
        cachingDataSource.cacheDeviceSession(
            deviceId, sessionId
        )
    }

    override suspend fun clearCache() {
        cachingDataSource.clearCache()
    }
}