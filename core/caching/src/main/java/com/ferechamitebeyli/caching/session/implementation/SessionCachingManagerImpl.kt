package com.ferechamitebeyli.caching.session.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ferechamitebeyli.caching.session.abstraction.SessionCachingManager
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_DEPARTURE_DATE
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_DESTINATION
import com.ferechamitebeyli.caching.util.CachingConstants.CACHE_KEY_FOR_ORIGIN
import com.ferechamitebeyli.caching.util.CachingConstants.OBILET_CACHE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(OBILET_CACHE_NAME)

@Singleton
class SessionCachingManagerImpl @Inject constructor(
    @ApplicationContext appContext: Context
): SessionCachingManager {

    private val oBiletDataStore = appContext.dataStore

    companion object {
        val lastQueriedOrigin = stringPreferencesKey(CACHE_KEY_FOR_ORIGIN)
        val lastQueriedDestination = stringPreferencesKey(CACHE_KEY_FOR_DESTINATION)
        val lastQueriedDepartureDate = stringPreferencesKey(CACHE_KEY_FOR_DEPARTURE_DATE)
    }

    override suspend fun cacheLastQueries(
        origin: String,
        destination: String,
        departureDate: String
    ) {
        oBiletDataStore.edit {
            it[lastQueriedOrigin] = origin
            it[lastQueriedDestination] = destination
            it[lastQueriedDepartureDate] = departureDate
        }
    }

    suspend fun clearCache() {
        oBiletDataStore.edit {
            it[lastQueriedOrigin] = ""
            it[lastQueriedDestination] = ""
            it[lastQueriedDepartureDate] = ""
        }
    }
}