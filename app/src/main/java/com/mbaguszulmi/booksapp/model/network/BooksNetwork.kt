package com.mbaguszulmi.booksapp.model.network

data class BooksNetwork(
    val id: String,
    val volumeInfo: VolumeInfo?,
    val saleInfo: SaleInfo?
) {
    data class VolumeInfo(
        val title: String?,
        val subtitle: String?,
        val authors: List<String>?,
        val publisher: String?,
        val publishedDate: String?,
        val description: String?,
        val imageLinks: ImageLinks?,
        val language: String?,
        val previewLink: String?,
        val infoLink: String?,
        val canonicalVolumeLink: String?,
        val categories: List<String>?
    ) {
        data class ImageLinks(
            val smallThumbnail: String?,
            val thumbnail: String?
        )

        fun getAuthorsStr(): String = authors?.joinToString(",") ?: ""
        fun getCategoriesStr(): String = categories?.joinToString(",") ?: ""
    }

    data class SaleInfo(
        val buyLink: String?,
        val listPrice: Price?,
        val retailPrice: Price?
    ) {
        data class Price(
            val amount: Double,
            val currencyCode: String
        )
    }
}
