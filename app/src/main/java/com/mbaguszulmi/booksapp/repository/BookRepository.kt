package com.mbaguszulmi.booksapp.repository

import com.mbaguszulmi.booksapp.model.local.Books
import com.mbaguszulmi.booksapp.service.network.BookApiService

class BookRepository(private val bookApiService: BookApiService) {
    suspend fun searchBooks(q: String): List<Books> {
        val response = bookApiService.searchBooks(q)

        if (response.isSuccessful) return response.body()!!.getBooksList()

        return listOf()
    }

    suspend fun getBookDetails(id: String): Books? {
        val response = bookApiService.getBookDetails(id)

        if (response.isSuccessful) return Books.fromBooksNetwork(response.body()!!)

        return null
    }

    fun getFavoriteBooks(): List<Books> {
        return listOf()
    }
}