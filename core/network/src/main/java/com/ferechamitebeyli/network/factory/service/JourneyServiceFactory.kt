package com.ferechamitebeyli.network.factory.service

import com.ferechamitebeyli.network.factory.base.RetrofitServiceFactory
import com.ferechamitebeyli.network.service.journey.JourneyService
import retrofit2.Retrofit

object JourneyServiceFactory : RetrofitServiceFactory<JourneyService> {
    override fun createService(retrofit: Retrofit): JourneyService {
        return retrofit.create(JourneyService::class.java)
    }
}