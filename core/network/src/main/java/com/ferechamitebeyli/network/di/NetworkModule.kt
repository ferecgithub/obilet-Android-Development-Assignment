package com.ferechamitebeyli.network.di

import com.ferechamitebeyli.network.BuildConfig
import com.ferechamitebeyli.network.factory.RemoteFactory
import com.ferechamitebeyli.network.interceptor.TokenInterceptor
import com.ferechamitebeyli.network.service.client.ClientService
import com.ferechamitebeyli.network.service.journey.JourneyService
import com.ferechamitebeyli.network.service.location.LocationService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

    @[Provides Singleton]
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @[Provides Singleton]
    fun provideRetrofit(
        remoteFactory: RemoteFactory,
        tokenInterceptor: TokenInterceptor
    ): Retrofit {
        return remoteFactory.createRetrofit(
            url = BuildConfig.BASE_URL,
            isDebug = BuildConfig.DEBUG,
            tokenInterceptor = tokenInterceptor
        )
    }

    @[Provides Singleton]
    fun provideClientService(retrofit: Retrofit): ClientService {
        return retrofit.create()
    }

    @[Provides Singleton]
    fun provideLocationService(retrofit: Retrofit): LocationService {
        return retrofit.create()
    }

    @[Provides Singleton]
    fun provideJourneyService(retrofit: Retrofit): JourneyService {
        return retrofit.create()
    }
}