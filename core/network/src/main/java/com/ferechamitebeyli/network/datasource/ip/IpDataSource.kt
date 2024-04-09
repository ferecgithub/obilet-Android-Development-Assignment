package com.ferechamitebeyli.network.datasource.ip

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object IpDataSource {
    var publicIpAddress = ""

    init {
        getPublicIpAddress()
    }

    private fun getPublicIpAddress() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.ipify.org/")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    responseBody?.let { ipAddress ->
                        publicIpAddress = ipAddress
                    }
                    println("Your outbound IP address is: $responseBody")
                } else {
                    println("Failed to get IP address: ${response.code}")
                }
            }
        })
    }



}