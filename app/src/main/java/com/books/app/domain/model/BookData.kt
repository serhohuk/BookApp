package com.books.app.domain.model

import com.google.gson.annotations.SerializedName

data class BookData(
    val books: List<Book>,
    val topBannerSlides: List<TopBannerSlide>,
    val youWillLikeSection: List<Int>
)

data class Book(
    val author: String,
    val coverUrl: String,
    val genre: String,
    val id: Int,
    val likes: String,
    val name: String,
    val quotes: String,
    val summary: String,
    val views: String
)

data class TopBannerSlide(
    val bookId: Int,
    val cover: String,
    val id: Int
)