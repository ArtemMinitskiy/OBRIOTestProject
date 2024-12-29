package com.example.obriotestproject.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.obriotestproject.model.UserTransaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM UserTransaction ORDER BY date DESC LIMIT :limit OFFSET :offset")
    suspend fun getTransactionsPag(limit: Int, offset: Int): List<UserTransaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(userTransaction: UserTransaction)
}