package com.example.obriotestproject.room

import android.util.Log
import com.example.obriotestproject.model.UserTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {
    suspend fun insertTransaction(transaction: UserTransaction) {
        withContext(Dispatchers.IO) {
            transactionDao.insertTransaction(transaction)
        }
    }

    suspend fun getTransactionsPag(limit: Int, offset: Int): List<UserTransaction> {
        Log.e("mLog", "getTransactionsPag limit $limit offset $offset")
        return withContext(Dispatchers.IO) {
            return@withContext transactionDao.getTransactionsPag(limit, offset)
        }
    }
}