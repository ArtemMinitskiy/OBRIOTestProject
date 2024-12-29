package com.example.obriotestproject.room

import com.example.obriotestproject.model.User
import com.example.obriotestproject.utils.Constants.USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun addUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertOrUpdate(user)
        }
    }

    suspend fun getUser() = userDao.getUserById(USER_ID)

    suspend fun doesUserExist() = userDao.doesUserExist(USER_ID)

    suspend fun updateLastSeen() = userDao.updateLastSeen(USER_ID, System.currentTimeMillis())

    suspend fun updateUserAmount(amount: Double) {
        withContext(Dispatchers.IO) {
            userDao.updateAmount(USER_ID, amount)
        }
    }

    suspend fun updateUserLastExchangeRate(exchangeRate: Double) {
        withContext(Dispatchers.IO) {
            userDao.updateUserLastExchangeRate(USER_ID, exchangeRate)
        }
    }
}