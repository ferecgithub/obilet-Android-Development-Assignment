package com.ferechamitebeyli.network.factory

import com.ferechamitebeyli.network.interceptor.TokenInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

class RemoteFactory @Inject constructor(private val moshi: Moshi) {

    fun createRetrofit(
        url: String,
        isDebug: Boolean,
        tokenInterceptor: TokenInterceptor
    ): Retrofit {
        val client = makeOkHttpClient(
            httpLoggingInterceptor = makeLoggingInterceptor(isDebug = isDebug),
            tokenInterceptor = tokenInterceptor
        )

        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES)
            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}