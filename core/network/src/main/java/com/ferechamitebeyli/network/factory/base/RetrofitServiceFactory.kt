package com.ferechamitebeyli.network.factory.base

import com.ferechamitebeyli.network.service.base.BaseService
import retrofit2.Retrofit

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

interface RetrofitServiceFactory<T : BaseService> {
    fun createService(retrofit: Retrofit): T
}