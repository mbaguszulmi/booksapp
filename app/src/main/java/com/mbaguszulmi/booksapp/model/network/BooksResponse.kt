package com.mbaguszulmi.booksapp.model.network

import com.mbaguszulmi.booksapp.model.local.Books

data class BooksResponse(
    val totalItems: Int,
    val items: List<BooksNetwork>
) {
    fun getBooksList() = items.map {
        Books.fromBooksNetwork(it)
    }
}
