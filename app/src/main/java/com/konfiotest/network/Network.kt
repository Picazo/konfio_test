package com.konfiotest.network

import android.os.Handler
import android.os.Looper
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIME_OUT = 30L

fun createNetworkClient(baseUrl: String, debug: Boolean = true): Retrofit =
    retrofitClient(baseUrl, httpClient(debug))

private fun httpClient(debug: Boolean): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()

    if (debug) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        clientBuilder.run {
            addInterceptor(httpLoggingInterceptor)
        }
    }

    clientBuilder.run {
        connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        readTimeout(TIME_OUT, TimeUnit.SECONDS).build()
    }

    clientBuilder.addInterceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == 401) {
            Handler(Looper.getMainLooper()).post {
            }
        }
        if (response.code == 504) {
            Handler(Looper.getMainLooper()).post {
            }
        }
        response
    }

    return clientBuilder.build()
}

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
