package com.dahlaran.mysmallsmarthouse.utils

import android.content.Context
import com.dahlaran.mysmallsmarthouse.MySmallHouseApplication
import com.dahlaran.mysmallsmarthouse.R
import java.io.IOException
import java.io.InputStream

object FileUtils {

    private const val DEVICES_LIST_PATH = "deviceData.txt"

    fun getDevicesInputStream(): InputStream? {
        return MySmallHouseApplication.context.get()?.let { getDevicesInputStream(it) }
    }

    fun getDevicesInputStream(context: Context): InputStream? {
        try {
            return context.assets?.open(DEVICES_LIST_PATH)
        } catch (ex: IOException) {
            ex.printStackTrace()
            ErrorMessenger.errorMessageToShow(javaClass.name, R.string.error_input_failed)
        }
        return null
    }

    fun getStringFromLocalDeviceFile(context: Context? = null): String {
        val inputStream: InputStream? = context?.run {
            getDevicesInputStream(this)
        } ?: getDevicesInputStream()

        var deviceListString = ""

        try {
            val size = inputStream?.available() ?: return ""
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            deviceListString = String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            ErrorMessenger.errorMessageToShow(javaClass.name, R.string.error_read_failed)
        } finally {
            try {
                inputStream?.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                ErrorMessenger.errorMessageToSend(javaClass.name, "Cannot close the device file")
            }
        }
        return deviceListString
    }
}