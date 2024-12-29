package com.example.obriotestproject.retrofit

import com.example.obriotestproject.model.BtcToUsdResponse
import retrofit2.Response
import retrofit2.http.GET

interface BtcToUsdService {
    @GET("api/ticker/BTC/USD")
    suspend fun getBtcToUsd(): Response<BtcToUsdResponse>
}