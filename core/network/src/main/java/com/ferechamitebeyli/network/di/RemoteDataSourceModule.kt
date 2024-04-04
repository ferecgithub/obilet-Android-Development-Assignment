package com.ferechamitebeyli.network.di

import com.ferechamitebeyli.network.datasource.client.abstraction.ClientRemoteDataSource
import com.ferechamitebeyli.network.datasource.client.implementation.ClientRemoteDataSourceImpl
import com.ferechamitebeyli.network.datasource.journey.abstraction.JourneyRemoteDataSource
import com.ferechamitebeyli.network.datasource.journey.implementation.JourneyRemoteDataSourceImpl
import com.ferechamitebeyli.network.datasource.location.abstraction.LocationRemoteDataSource
import com.ferechamitebeyli.network.datasource.location.implementation.LocationRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

@[Module InstallIn(SingletonComponent::class)]
interface RemoteDataSourceModule {

    @get:Binds
    var ClientRemoteDataSourceImpl.clientRemoteDataSource: ClientRemoteDataSource

    @get:Binds
    var LocationRemoteDataSourceImpl.locationRemoteDataSource: LocationRemoteDataSource

    @get:Binds
    var JourneyRemoteDataSourceImpl.journeyRemoteDataSource: JourneyRemoteDataSource

}