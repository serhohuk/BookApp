package com.books.app.domain.interactor

import com.books.app.data.repository.BookRepository

class BookInteractor(
    private val repository: BookRepository
) {

    suspend fun getBookData() = repository.getBookData()!!.mapToDomain()

    suspend fun getBooks() = repository.getBooks().map { it.mapToDomain() }

    suspend fun getRecommendSections() = repository.getRecommendSections()

}