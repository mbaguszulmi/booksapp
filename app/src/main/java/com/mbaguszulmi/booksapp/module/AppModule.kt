package com.mbaguszulmi.booksapp.module

import com.mbaguszulmi.booksapp.repository.BookRepository
import com.mbaguszulmi.booksapp.service.network.BookApiFactory
import com.mbaguszulmi.booksapp.service.network.BookApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideBookApiService(): BookApiService = BookApiFactory.create()

    @Provides
    @Singleton
    fun provideBookRepository(bookApiService: BookApiService): BookRepository = BookRepository(bookApiService)
}
