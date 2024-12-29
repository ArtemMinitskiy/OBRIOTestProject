package com.example.obriotestproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obriotestproject.model.BtcToUsdResponse
import com.example.obriotestproject.model.User
import com.example.obriotestproject.model.UserTransaction
import com.example.obriotestproject.retrofit.NetworkResult
import com.example.obriotestproject.retrofit.RetrofitRepository
import com.example.obriotestproject.room.TransactionRepository
import com.example.obriotestproject.room.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val retrofitRepository: RetrofitRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _response: MutableLiveData<NetworkResult<BtcToUsdResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<BtcToUsdResponse>> = _response
    fun getBtcToUsd() = viewModelScope.launch {
        Log.i("mLogUser", "getBtcToUsd")
        retrofitRepository.getBtcToUsd().collect { values ->
            _response.value = values
        }
    }

    private var currentOffset = 0
    private val pageSize = 20

    private val _items = MutableStateFlow<List<UserTransaction>>(emptyList())
    val items: StateFlow<List<UserTransaction>> = _items

    init {
        loadMoreItems()
    }

    fun loadMoreItems() {
        viewModelScope.launch {
            val newItems =
                transactionRepository.getTransactionsPag(offset = currentOffset, limit = pageSize)
                    .sortedByDescending { it.date }
            currentOffset += newItems.size
            _items.value = _items.value + newItems
        }
    }

    fun addTransaction(amount: Double, category: String, date: Long = System.currentTimeMillis()) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.insertTransaction(
                UserTransaction(
                    amount = amount,
                    category = category,
                    date = date
                )
            )
        }.invokeOnCompletion {
            Log.e("mLog", "Added")
            viewModelScope.launch {
                val newItems =
                    transactionRepository.getTransactionsPag(offset = 0, limit = 1)

                newItems.forEach {
                    Log.i("mLog", "${it.amount}")
                }
                currentOffset += newItems.size
                _items.value = _items.value + newItems
            }
        }
    }

    fun addUser(user: User) {
        Log.i("mLog", "addUser")
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUser(user)
        }.invokeOnCompletion {
            Log.i("mLog", "User Added")
            getUser()
        }
    }

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user
    fun getUser() {
        Log.i("mLog", "getUser")
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUser()?.let {
                _user.value = it
            }
        }
    }
    suspend fun doesUserExist() : Boolean = userRepository.doesUserExist()

    fun updateUserAmount(amount: Double) {
        Log.i("mLog", "updateUserAmount $amount")
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUserAmount(amount)
        }.invokeOnCompletion {
            getUser()
        }
    }

    fun updateUserLastExchangeRate(exchangeRate: Double) {
        Log.i("mLog", "updateUserLastExchangeRate $exchangeRate")
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUserLastExchangeRate(exchangeRate)
        }.invokeOnCompletion {
            getUser()
        }
    }

    fun updateLastSeen() {
        Log.i("mLog", "updateLastSeen")
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateLastSeen()
        }.invokeOnCompletion {
            getUser()
        }
    }
}