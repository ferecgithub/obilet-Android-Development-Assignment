package com.ferechamitebeyli.caching.di

import android.content.Context
import com.ferechamitebeyli.caching.session.abstraction.SessionCachingManager
import com.ferechamitebeyli.caching.session.implementation.SessionCachingManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object CachingModule {

    @[Provides Singleton]
    fun provideDataStoreObject(@ApplicationContext appContext: Context): SessionCachingManager =
        SessionCachingManagerImpl(appContext)


}