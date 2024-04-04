package com.ferechamitebeyli.network.di

import com.ferechamitebeyli.network.datasource.client.abstraction.ClientRemoteDataSource
import com.ferechamitebeyli.network.datasource.client.implementation.ClientRemoteDataSourceImpl
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

}