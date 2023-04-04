package com.gustavo.playerspotify.extentions

import java.text.SimpleDateFormat
import java.util.*

fun String.convertMSToSeconds(ml: Long): String {
    val date = Date(ml)
    val formatter = SimpleDateFormat("mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}