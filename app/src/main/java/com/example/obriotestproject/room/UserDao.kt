package com.example.obriotestproject.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.obriotestproject.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: User)

    @Query("UPDATE User SET balance = :newAmount WHERE userId = :userId")
    suspend fun updateAmount(userId: Long, newAmount: Double)

    @Query("UPDATE User SET lastSeen = :newLastSeen WHERE userId = :userId")
    suspend fun updateLastSeen(userId: Long, newLastSeen: Long)

    @Query("SELECT * FROM User WHERE userId = :userId")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT EXISTS(SELECT 1 FROM User WHERE userId = :userId)")
    suspend fun doesUserExist(userId: Long): Boolean

    @Query("UPDATE User SET lastExchangeRate = :exchangeRate WHERE userId = :userId")
    suspend fun updateUserLastExchangeRate(userId: Long, exchangeRate: Double)
}