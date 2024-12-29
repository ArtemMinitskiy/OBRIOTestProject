package com.example.obriotestproject.model

import com.google.gson.annotations.SerializedName

data class BtcToUsdResponse(
    @SerializedName("ask")
    val ask: Double
)