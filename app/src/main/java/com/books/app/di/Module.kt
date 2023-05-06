package com.books.app.di

import com.books.app.data.repository.BookRepository
import com.books.app.domain.interactor.BookInteractor
import com.books.app.presentation.details.BookDetailsViewModel
import com.books.app.presentation.main.MainViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single {
        Firebase.remoteConfig
    }

    factory {
        Gson()
    }

}

val screenModule = module {

    factory {
        BookRepository(get(), get())
    }

    factory {
        BookInteractor(get())
    }

    viewModel {
        MainViewModel(get())
    }

    viewModel { (bookId: Int) ->
        BookDetailsViewModel(bookId, get())
    }

}