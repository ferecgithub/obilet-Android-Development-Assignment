package com.ferechamitebeyli.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

class TokenInterceptor : Interceptor {

    companion object {
        private const val TOKEN_KEY = "Basic"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        val token = "JEcYcEMyantZV095WVc3G2JtVjNZbWx1"
        requestBuilder.header(TOKEN_KEY, token)
        val newRequest = requestBuilder.build()
        return try {
            chain.proceed(newRequest)
        } catch (e: Exception) {
            throw e
        }
    }

}