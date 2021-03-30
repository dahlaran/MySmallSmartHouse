package com.dahlaran.mysmallsmarthouse.utils

import android.annotation.SuppressLint
import android.text.format.DateFormat
import java.text.SimpleDateFormat


object DateUtils {
    private const val dateTimeFormat: String = "dd/MM/yyyy"

    fun convertLongToDateString(dateLong: Long): String {
        return DateFormat.format(dateTimeFormat, dateLong).toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateStringToLong(dateString: String): Long {
        return SimpleDateFormat(dateTimeFormat).parse(dateString)!!.time
    }
}