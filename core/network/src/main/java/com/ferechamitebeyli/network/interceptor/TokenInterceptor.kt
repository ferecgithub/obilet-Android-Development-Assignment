package com.ferechamitebeyli.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

class TokenInterceptor @Inject constructor() : Interceptor {

    companion object {
        private const val TOKEN_KEY = "Basic"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        val token = "JEcYcEMyantZV095WVc3G2JtVjNZbWx1"
        requestBuilder.header("Authorization", "Basic $token");
        val newRequest = requestBuilder.build()
        return try {
            chain.proceed(newRequest)
        } catch (e: Exception) {
            throw e
        }
    }

}