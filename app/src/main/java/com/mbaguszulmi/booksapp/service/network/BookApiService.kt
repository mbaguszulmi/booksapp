package com.mbaguszulmi.booksapp.service.network

import com.mbaguszulmi.booksapp.model.network.BooksNetwork
import com.mbaguszulmi.booksapp.model.network.BooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {

    @GET("volumes")
    suspend fun searchBooks(@Query("q") q: String): Response<BooksResponse>

    @GET("volumes/{id}")
    suspend fun getBookDetails(@Path("id") id: String): Response<BooksNetwork>
}
