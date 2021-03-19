package com.dahlaran.mysmallsmarthouse.utils

import android.text.format.DateFormat


object DateUtils {
    private const val dateTimeFormat: String = "dd-MM-yyyy"

    fun convertLongToDateString(dateLong: Long): String {
        return DateFormat.format(dateTimeFormat, dateLong).toString()
    }
}