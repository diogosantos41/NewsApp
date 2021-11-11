package com.dscode.newsapp.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_DISPLAY_FORMAT = "dd/MM/yyyy HH:mm";

class NumberUtils {
    fun formatServerDateToDisplayDate(date: Date): String {
        // TODO convert fun here
        return SimpleDateFormat(DATE_DISPLAY_FORMAT).format(date)
    }
}