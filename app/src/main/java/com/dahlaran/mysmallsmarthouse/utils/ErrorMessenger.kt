package com.dahlaran.mysmallsmarthouse.utils

import android.widget.Toast
import com.dahlaran.mysmallsmarthouse.MySmallHouseApplication

object ErrorMessenger {

    fun errorMessageToSend(tag: String, message: String) {
        CustomLogger.logE(tag, message)
    }

    fun errorMessageToShow(tag: String, messageId: Int) {
        MySmallHouseApplication.context.get()?.let {
            val message = it.getString(messageId)
            Toast.makeText(it, message, Toast.LENGTH_LONG).show()

            CustomLogger.logE(tag, message)
        }
    }
}