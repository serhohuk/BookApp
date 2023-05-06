package com.books.app.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.domain.interactor.BookInteractor
import com.books.app.domain.model.Book
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    val bookId: Int,
    private val interactor: BookInteractor
) : ViewModel() {

    private val _uiState: MutableStateFlow<BookDetailsUIState> =
        MutableStateFlow(BookDetailsUIState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _uiMessage = MutableSharedFlow<String?>()
    val uiMessage = _uiMessage.asSharedFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            try {
                val books = interactor.getBooks()
                val alsoLike = interactor.getRecommendSections()
                _uiState.value = BookDetailsUIState.Success(books, books.filter { alsoLike.contains(it.id) })
            } catch (e: Exception) {
                e.printStackTrace()
                _uiMessage.emit("Something went wrong")
            }
        }
    }

}