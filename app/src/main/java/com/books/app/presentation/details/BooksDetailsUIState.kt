package com.books.app.presentation.details

import com.books.app.domain.model.Book

sealed class BookDetailsUIState {
    object Loading : BookDetailsUIState()
    data class Success(
        val list: List<Book>,
        val alsoLike: List<Book>
    ) : BookDetailsUIState()
}