package com.example.obriotestproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0L,
    val balance: Double = 0.0,
    val lastSeen: Long = 0L,
    val lastExchangeRate: Double = 0.0
)
