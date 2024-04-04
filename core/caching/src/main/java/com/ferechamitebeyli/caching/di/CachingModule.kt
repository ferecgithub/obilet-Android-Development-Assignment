package com.ferechamitebeyli.caching.di

import android.content.Context
import com.ferechamitebeyli.caching.session.abstraction.SessionCachingManager
import com.ferechamitebeyli.caching.session.implementation.SessionCachingManagerImpl
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

object CachingModule {

    @[Provides Singleton]
    fun provideDataStoreObject(@ApplicationContext appContext: Context): SessionCachingManager =
        SessionCachingManagerImpl(appContext)


}