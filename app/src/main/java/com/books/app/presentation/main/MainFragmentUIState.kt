package com.books.app.presentation.main

import com.books.app.domain.model.Book
import com.books.app.domain.model.TopBannerSlide

sealed class MainFragmentUIState {
    object Loading : MainFragmentUIState()
    data class Success(
        val sliderBanners: List<TopBannerSlide>,
        val booksMap: Map<String, List<Book>>
    ) : MainFragmentUIState()
}