package com.dahlaran.mysmallsmarthouse.utils

import com.dahlaran.mysmallsmarthouse.R

object EditTextUtils {

    private const val MAX_TEXT_SIZE = 256

    fun maxTextVerifier(text: String): Int {
        if ((text.length >= MAX_TEXT_SIZE)) {
            return R.string.error_field_to_long
        }
        return 0
    }

    fun simpleTextVerifier(text: String): Int {
        val verify = maxTextVerifier(text)
        if (verify != 0) {
            return verify
        } else if (text.isEmpty()) {
            return R.string.error_field_blank
        }
        return 0
    }

    fun noNumberTextVerifier(text: String): Int {
        val verify = simpleTextVerifier(text)
        if (verify != 0) {
            return verify
        }

        if (text.matches(Regex(".*\\d.*"))) {
            return R.string.error_field_number_inside
        }

        return 0
    }

    fun onlyNumberTextVerifier(text: String): Int {
        val verify = simpleTextVerifier(text)
        if (verify != 0) {
            return verify
        }

        if (!text.matches(Regex("^[0-9/-]*\$"))) {
            return R.string.error_field_only_number
        }

        return 0
    }
}