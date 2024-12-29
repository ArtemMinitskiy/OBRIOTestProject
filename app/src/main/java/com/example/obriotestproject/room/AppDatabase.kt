package com.example.obriotestproject.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.obriotestproject.model.User
import com.example.obriotestproject.model.UserTransaction

@Database(entities = [UserTransaction::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao
}