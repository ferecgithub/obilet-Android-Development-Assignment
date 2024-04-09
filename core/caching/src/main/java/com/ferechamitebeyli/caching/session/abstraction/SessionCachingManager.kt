package com.ferechamitebeyli.caching.session.abstraction

import kotlinx.coroutines.flow.Flow

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface SessionCachingManager {

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
    fun getLastQueriedOriginName(): Flow<String>
    fun getLastQueriedOriginId(): Flow<Int>
    fun getLastQueriedDestinationName(): Flow<String>
    fun getLastQueriedDestinationId(): Flow<Int>
    fun getLastQueriedDepartureDateForService(): Flow<String>
    fun getLastQueriedDepartureDateForUi(): Flow<String>
    fun getCachedDeviceId(): Flow<String>
    fun getCachedSessionId(): Flow<String>
    suspend fun clearCache()
}