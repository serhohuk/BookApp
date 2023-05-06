package com.books.app.data.model

import com.books.app.domain.model.BookData
import com.google.gson.annotations.SerializedName

data class BookData(
    @SerializedName("books")
    val books: List<Book>,
    @SerializedName("top_banner_slides")
    val topBannerSlides: List<TopBannerSlide>?,
    @SerializedName("you_will_like_section")
    val youWillLikeSection: List<Int>?
) {

    fun mapToDomain(): com.books.app.domain.model.BookData {
        return com.books.app.domain.model.BookData(
            books.map { it.mapToDomain() },
            topBannerSlides?.map { it.mapToDomain() }?: emptyList(),
            youWillLikeSection?: emptyList()
        )
    }

}

data class Book(
    @SerializedName("author")
    val author: String,
    @SerializedName("cover_url")
    val coverUrl: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("likes")
    val likes: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("quotes")
    val quotes: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("views")
    val views: String
) {

    fun mapToDomain(): com.books.app.domain.model.Book {
        return com.books.app.domain.model.Book(
            author, coverUrl, genre, id, likes, name, quotes, summary, views
        )
    }

}

data class TopBannerSlide(
    @SerializedName("book_id")
    val bookId: Int,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("id")
    val id: Int
) {

    fun mapToDomain(): com.books.app.domain.model.TopBannerSlide {
        return com.books.app.domain.model.TopBannerSlide(
            bookId, cover, id
        )
    }

}