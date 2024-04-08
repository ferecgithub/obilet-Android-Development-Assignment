package com.ferechamitebeyli.caching.session.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ferechamitebeyli.caching.session.abstraction.SessionCachingManager
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_DEPARTURE_DATE_FOR_SERVICE
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_DEPARTURE_DATE_FOR_UI
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_DESTINATION_ID
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_DESTINATION_NAME
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_DEVICE_ID
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_ORIGIN_ID
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_ORIGIN_NAME
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_SESSION_ID
import com.ferechamitebeyli.caching.util.CachingConstants.OBILET_CACHE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(OBILET_CACHE_NAME)

@Singleton
class SessionCachingManagerImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : SessionCachingManager {

    private val oBiletDataStore = appContext.dataStore

    companion object {
        val lastQueriedOriginName = stringPreferencesKey(CACHE_KEY_FOR_ORIGIN_NAME)
        val lastQueriedOriginId = intPreferencesKey(CACHE_KEY_FOR_ORIGIN_ID)
        val lastQueriedDestinationName = stringPreferencesKey(CACHE_KEY_FOR_DESTINATION_NAME)
        val lastQueriedDestinationId = intPreferencesKey(CACHE_KEY_FOR_DESTINATION_ID)
        val lastQueriedDepartureDateForService =
            stringPreferencesKey(CACHE_KEY_FOR_DEPARTURE_DATE_FOR_SERVICE)
        val lastQueriedDepartureDateForUi =
            stringPreferencesKey(CACHE_KEY_FOR_DEPARTURE_DATE_FOR_UI)
        val cachedDeviceId = stringPreferencesKey(CACHE_KEY_FOR_DEVICE_ID)
        val cachedSessionId = stringPreferencesKey(CACHE_KEY_FOR_SESSION_ID)
    }

    override suspend fun cacheLastQueries(
        originName: String,
        originId: Int,
        destinationName: String,
        destinationId: Int,
        departureDateForService: String,
        departureDateForUi: String,
    ) {
        oBiletDataStore.edit {
            it[lastQueriedOriginName] = originName
            it[lastQueriedOriginId] = originId
            it[lastQueriedDestinationName] = destinationName
            it[lastQueriedDestinationId] = destinationId
            it[lastQueriedDepartureDateForService] = departureDateForService
            it[lastQueriedDepartureDateForUi] = departureDateForUi
        }
    }

    override suspend fun cacheDeviceSession(deviceId: String, sessionId: String) {
        oBiletDataStore.edit {
            it[cachedDeviceId] = deviceId
            it[cachedSessionId] = sessionId
        }
    }

    override fun getLastQueriedOriginName(): Flow<String> = oBiletDataStore.data.map {
        it[lastQueriedOriginName] ?: ""
    }

    override fun getLastQueriedOriginId(): Flow<Int> = oBiletDataStore.data.map {
        it[lastQueriedOriginId] ?: -1
    }


    override fun getLastQueriedDestinationName(): Flow<String> = oBiletDataStore.data.map {
        it[lastQueriedDestinationName] ?: ""
    }

    override fun getLastQueriedDestinationId(): Flow<Int> = oBiletDataStore.data.map {
        it[lastQueriedDestinationId] ?: -1
    }

    override fun getLastQueriedDepartureDateForService() = oBiletDataStore.data.map {
        it[lastQueriedDepartureDateForService] ?: ""
    }

    override fun getLastQueriedDepartureDateForUi() = oBiletDataStore.data.map {
        it[lastQueriedDepartureDateForUi] ?: ""
    }

    override fun getCachedDeviceId() = oBiletDataStore.data.map {
        it[cachedDeviceId] ?: ""
    }

    override fun getCachedSessionId() = oBiletDataStore.data.map {
        it[cachedSessionId] ?: ""
    }

    override suspend fun clearCache() {
        oBiletDataStore.edit {
            it[lastQueriedOriginName] = ""
            it[lastQueriedOriginId] = -1
            it[lastQueriedDestinationName] = ""
            it[lastQueriedDestinationId] = -1
            it[lastQueriedDepartureDateForService] = ""
            it[lastQueriedDepartureDateForUi] = ""
            it[cachedDeviceId] = ""
            it[cachedSessionId] = ""
        }
    }
}