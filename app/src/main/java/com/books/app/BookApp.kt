package com.books.app

import android.app.Application
import com.books.app.di.appModule
import com.books.app.di.screenModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class BookApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookApp)
            modules(appModule, screenModule)
        }
    }

}