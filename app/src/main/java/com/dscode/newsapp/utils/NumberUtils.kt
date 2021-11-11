package com.dscode.newsapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val DATE_DISPLAY_FORMAT = "MMM d, HH:mm";


fun convertServerDateToDisplayDate(data: String): String {
    if (data.isEmpty()) {
        return "-"
    }
    return try {
        val initDate: Date = SimpleDateFormat(DATE_SERVER_FORMAT).parse(data)    // parse input
        SimpleDateFormat(DATE_DISPLAY_FORMAT).format(initDate)    // format output
    } catch (e: ParseException) {
        "-"
    }
}