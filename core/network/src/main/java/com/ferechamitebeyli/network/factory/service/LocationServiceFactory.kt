package com.ferechamitebeyli.network.factory.service

import com.ferechamitebeyli.network.factory.base.RetrofitServiceFactory
import com.ferechamitebeyli.network.service.location.LocationService
import retrofit2.Retrofit

object LocationServiceFactory : RetrofitServiceFactory<LocationService> {
    override fun createService(retrofit: Retrofit): LocationService {
        return retrofit.create(LocationService::class.java)
    }
}