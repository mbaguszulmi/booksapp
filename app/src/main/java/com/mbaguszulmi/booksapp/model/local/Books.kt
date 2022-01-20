package com.mbaguszulmi.booksapp.model.local

import java.text.SimpleDateFormat
import com.mbaguszulmi.booksapp.model.network.BooksNetwork
import java.lang.Exception
import java.text.NumberFormat
import java.util.*

data class Books(
    val id: String,
    val title: String?,
    val subtitle: String?,
    val authors: String?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val language: String?,
    val previewLink: String?,
    val infoLink: String?,
    val canonicalVolumeLink: String?,
    val smallThumbnail: String?,
    val thumbnail: String?,
    val categories: String?,
    val buyLink: String?,
    val listPriceAmount: Double?,
    val listPriceCurrencyCode: String?,
    val retailAmount: Double?,
    val retailCurrencyCode: String?
) {
    companion object {
        @JvmStatic
        fun fromBooksNetwork(booksNetwork: BooksNetwork): Books {
            return Books(
                booksNetwork.id,
                booksNetwork.volumeInfo?.title,
                booksNetwork.volumeInfo?.subtitle,
                booksNetwork.volumeInfo?.getAuthorsStr(),
                booksNetwork.volumeInfo?.publisher,
                booksNetwork.volumeInfo?.publishedDate,
                booksNetwork.volumeInfo?.description,
                booksNetwork.volumeInfo?.language,
                booksNetwork.volumeInfo?.previewLink,
                booksNetwork.volumeInfo?.infoLink,
                booksNetwork.volumeInfo?.canonicalVolumeLink,
                booksNetwork.volumeInfo?.imageLinks?.smallThumbnail,
                booksNetwork.volumeInfo?.imageLinks?.thumbnail,
                booksNetwork.volumeInfo?.getCategoriesStr(),
                booksNetwork.saleInfo?.buyLink,
                booksNetwork.saleInfo?.listPrice?.amount,
                booksNetwork.saleInfo?.listPrice?.currencyCode,
                booksNetwork.saleInfo?.retailPrice?.amount,
                booksNetwork.saleInfo?.retailPrice?.currencyCode
            )
        }
    }

    fun getYear(): String {
        return publishedDate?.let {
            val date: Date = parseDate(it) ?: return ""
            SimpleDateFormat("yyyy", Locale.US).format(date)
        } ?: ""
    }

    private fun parseDate(str: String): Date? = try {
            SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(str)
        } catch (e: Exception) {
            try {
                SimpleDateFormat("yyyy-MM", Locale.US).parse(str)
            } catch (e: Exception) {
                try {
                    SimpleDateFormat("yyyy", Locale.US).parse(str)
                } catch (e: Exception) {
                    null
                }
            }
        }

    val formattedPublishedDate get(): String {
        return publishedDate?.let {
            val date: Date = parseDate(it) ?: return ""
            SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date)
        } ?: ""
    }

    val formattedPrice get(): String {
        if (listPriceAmount == null) {
            return ""
        }

        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance(listPriceCurrencyCode)
        return format.format(listPriceAmount)
    }
}
