package com.ferechamitebeyli.caching.session.abstraction

import kotlinx.coroutines.flow.Flow

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface SessionCachingManager {
    suspend fun cacheLastQueries(origin: String, destination: String, departureDate: String)
    suspend fun cacheDeviceSession(deviceId: String, sessionId: String)

    fun getLastQueriedOrigin(): Flow<String>
    fun getLastQueriedDestination(): Flow<String>
    fun getLastQueriedDepartureDate(): Flow<String>
    fun getCachedDeviceId(): Flow<String>
    fun getCachedSessionId(): Flow<String>
    suspend fun clearCache()
}