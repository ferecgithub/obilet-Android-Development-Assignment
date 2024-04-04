package com.ferechamitebeyli.caching.session.abstraction

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface SessionCachingManager {
    suspend fun cacheLastQueries(origin: String, destination: String, departureDate: String)
    suspend fun clearCache()
}