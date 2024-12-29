package com.example.obriotestproject.di

import com.example.obriotestproject.MainActivity
import com.example.obriotestproject.room.DatabaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}