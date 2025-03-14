package com.insa.mygamelist.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import okhttp3.Interceptor

object IGDBClient {
    private const val BASE_URL = "https://api.igdb.com/v4/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor {
            it.proceed(
                it.request().newBuilder()
                    .addHeader("Client-ID", "sg16951bcbb49ntm5w2ma13r5vqtje")
                    .addHeader("Authorization", "Bearer fw82xpyov5o46p7rwjxfjc1c0afr6l")
                    .build()
            )
        })
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val api: IGDBApi by lazy {
        retrofit.create(IGDBApi::class.java)
    }
}