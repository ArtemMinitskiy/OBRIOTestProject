package com.example.obriotestproject

import android.app.Application
import com.example.obriotestproject.di.AppComponent
import com.example.obriotestproject.di.DaggerAppComponent
import com.example.obriotestproject.room.DatabaseModule

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}