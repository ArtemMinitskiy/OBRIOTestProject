package com.example.obriotestproject.utils

import com.example.obriotestproject.utils.Constants.ONE_HOUR

fun isLastSeenHourAgo(lastSeen: Long): Boolean {
    return System.currentTimeMillis() - lastSeen > ONE_HOUR
}