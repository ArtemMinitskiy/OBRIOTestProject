package com.example.obriotestproject.retrofit

import com.example.obriotestproject.model.BtcToUsdResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {
    suspend fun getBtcToUsd(): Flow<NetworkResult<BtcToUsdResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getBtcToUsd() })
        }.flowOn(Dispatchers.IO)
    }
}