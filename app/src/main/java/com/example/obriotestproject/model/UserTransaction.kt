package com.example.obriotestproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserTransaction(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Long? = null,
    val amount: Double,
    val category: String,
    val date: Long,
)
