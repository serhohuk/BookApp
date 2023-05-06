package com.books.app.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.domain.interactor.BookInteractor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val interactor: BookInteractor
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainFragmentUIState> =
        MutableStateFlow(MainFragmentUIState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _uiMessage = MutableSharedFlow<String?>()
    val uiMessage = _uiMessage.asSharedFlow()

    init {
        loadBookData()
    }

    private fun loadBookData() {
        viewModelScope.launch {
            try {
                val books = interactor.getBookData()
                _uiState.value = MainFragmentUIState.Success(
                    books.topBannerSlides,
                    books.books.groupBy { it.genre }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _uiMessage.emit("Something went wrong")
            }
        }
    }

}