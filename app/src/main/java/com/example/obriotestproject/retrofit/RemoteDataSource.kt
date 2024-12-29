package com.example.obriotestproject.retrofit

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val btcToUsdService: BtcToUsdService) {
    suspend fun getBtcToUsd() = btcToUsdService.getBtcToUsd()
}