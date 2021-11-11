package com.dscode.newsapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val DATE_DISPLAY_FORMAT = "MMM d, h:mm";


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

/*
if (TextUtils.isEmpty(var0)) {
        return null;
    } else if ("0001-01-01T00:00:00".equalsIgnoreCase(var0)) {
        return "-";
    } else {
        DateTimeFormatter var10000 = var0.length() < 20 ? DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss") : (var0.length() < 24 ? DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS") : DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ"));

        try {
            return DateTimeFormat.mediumDateTime().print(var10000.parseDateTime(var0));
        } catch (IllegalArgumentException var2) {
            return "-";
        }
 */