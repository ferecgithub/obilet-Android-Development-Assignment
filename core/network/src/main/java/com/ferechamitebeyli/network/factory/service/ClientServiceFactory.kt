package com.ferechamitebeyli.network.factory.service

import com.ferechamitebeyli.network.factory.base.RetrofitServiceFactory
import com.ferechamitebeyli.network.service.client.ClientService
import retrofit2.Retrofit

object ClientServiceFactory : RetrofitServiceFactory<ClientService> {
    override fun createService(retrofit: Retrofit): ClientService {
        return retrofit.create(ClientService::class.java)
    }
}