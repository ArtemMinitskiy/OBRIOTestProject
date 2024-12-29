package com.example.obriotestproject.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertLongToDateTimeString(timeInMillis: Long): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(Date(timeInMillis))
}