package com.mbaguszulmi.booksapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbaguszulmi.booksapp.R
import com.mbaguszulmi.booksapp.model.local.Books
import com.mbaguszulmi.booksapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {
    private val detailsLiveData = MutableLiveData<Books?>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val errorMessageLiveData = MutableLiveData<String?>()

    init {
        isLoadingLiveData.value = true
    }

    fun fetchDetails(id: String, context: Context) {
        isLoadingLiveData.value = true

        viewModelScope.launch {
            try {
                val book = bookRepository.getBookDetails(id)
                if (book == null) {
                    errorMessageLiveData.value = context.getString(R.string.book_fetch_failure)
                } else {
                    detailsLiveData.value = book
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessageLiveData.value = e.message
            }

            isLoadingLiveData.value = false
        }
    }

    val details get(): LiveData<Books?> = detailsLiveData
    val isLoading get(): LiveData<Boolean> = isLoadingLiveData
    val errorMessage get(): LiveData<String?> = errorMessageLiveData
}