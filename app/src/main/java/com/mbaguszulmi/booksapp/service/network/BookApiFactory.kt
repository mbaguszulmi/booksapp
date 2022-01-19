package com.mbaguszulmi.booksapp.service.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object BookApiFactory {
    private const val baseUrl = "https://www.googleapis.com/books/v1/"

    private val client: OkHttpClient get() = OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        addInterceptor{ chain ->
            chain.proceed(chain.request().newBuilder().build())
        }
    }.build()

    private val retrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        client(client)
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    fun create(): BookApiService = retrofit.create(BookApiService::class.java)
}