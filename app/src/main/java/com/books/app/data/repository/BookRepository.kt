package com.books.app.data.repository

import com.books.app.data.model.Book
import com.books.app.data.model.BookData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

class BookRepository(
    private val remoteConfig: FirebaseRemoteConfig,
    private val gson: Gson
) {

    private suspend fun fetchConfig(): FirebaseRemoteConfig {
        try {
            val cacheExpiration = 3600L
            remoteConfig.fetch(cacheExpiration).await()
            remoteConfig.activate().await()
        } catch (exception: Exception) {

        }
        return remoteConfig
    }

    suspend fun getBookData(): BookData? {
        fetchConfig()
        val jsonStr = remoteConfig.getString("json_data")
        return gson.fromJson(jsonStr, BookData::class.java)
    }

    suspend fun getBooks(): List<Book> {
        fetchConfig()
        val jsonStr = remoteConfig.getString("details_carousel")
        val response = gson.fromJson(jsonStr, BookData::class.java)
        return response.books
    }

    suspend fun getRecommendSections(): List<Int> {
        fetchConfig()
        val jsonStr = remoteConfig.getString("json_data")
        return gson.fromJson(jsonStr, BookData::class.java)!!.youWillLikeSection?: emptyList()
    }

}