package com.dahlaran.mysmallsmarthouse.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Before
import org.junit.Test

class FileUtilsTest {

    lateinit var context: Context

    @Before
    fun before() {
        context = getInstrumentation().context
    }

    /**
     * Check if the file exit and if it is accessible
     */
    @Test
    fun getDevicesInputStreamTest() {
        assert(FileUtils.getDevicesInputStream(context) != null)
    }

    /**
     * Check if the file exit, is accessible and get it's content in String format
     */
    @Test
    fun getStringFromLocalDeviceFileTest() {
        assert(FileUtils.getStringFromLocalDeviceFile(context) != null)
    }
}