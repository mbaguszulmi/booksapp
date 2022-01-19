package com.mbaguszulmi.booksapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.mbaguszulmi.booksapp.R
import com.mbaguszulmi.booksapp.model.local.Books
import com.mbaguszulmi.booksapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val bookRepository: BookRepository): ViewModel() {
    private var query = "Test Driven Development"
    private val booksMutableLiveData = MutableLiveData<List<Books>>()
    private val errorMessageLiveData = MutableLiveData<String?>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    var menuId = R.id.mi_home

    init {
        booksMutableLiveData.value = ArrayList()
        isLoadingLiveData.value = false

        searchBook()
    }

    fun searchBook() {
        if (menuId == R.id.mi_home) {
            isLoadingLiveData.value = true
            viewModelScope.launch {
                try {
                    val books = bookRepository.searchBooks(query)
                    Log.d("MainViewModel", "Fetched book size = ${books.size}")
                    booksMutableLiveData.value = (books)
                } catch (e: Exception) {
                    e.printStackTrace()
                    errorMessageLiveData.value = (e.message)
                }

                isLoadingLiveData.value = (false)
                Log.d("MainViewModel", "Data Refreshed")
            }

            return
        }

        // TODO: Search favorite
    }

    fun setQuery(q: String?) {
        if (q == null || q.isEmpty()) {
            query = "Test Driven Development"
            return
        }

        query = q
    }

    val books get(): LiveData<List<Books>> = booksMutableLiveData
    val booksValue get() = booksMutableLiveData.value ?: ArrayList()
    val isLoading get(): LiveData<Boolean> = isLoadingLiveData
    val errorMessage get(): LiveData<String?> = errorMessageLiveData
}