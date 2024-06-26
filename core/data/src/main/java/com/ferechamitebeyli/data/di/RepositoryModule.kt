package com.ferechamitebeyli.data.di

import com.ferechamitebeyli.data.repository.client.abstraction.ClientRepository
import com.ferechamitebeyli.data.repository.client.implementation.ClientRepositoryImpl
import com.ferechamitebeyli.data.repository.journey.abstraction.JourneyRepository
import com.ferechamitebeyli.data.repository.journey.implementation.JourneyRepositoryImpl
import com.ferechamitebeyli.data.repository.location.abstraction.LocationRepository
import com.ferechamitebeyli.data.repository.location.implementation.LocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

@[Module InstallIn(SingletonComponent::class)]
interface RepositoryModule {

    @get:Binds
    val ClientRepositoryImpl.clientRepository: ClientRepository

    @get:Binds
    val LocationRepositoryImpl.locationRepository: LocationRepository

    @get:Binds
    val JourneyRepositoryImpl.journeyRepository: JourneyRepository
}