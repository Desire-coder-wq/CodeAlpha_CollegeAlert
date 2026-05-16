package com.codealpha.collegealert.util

import android.content.Context
import android.util.Log
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Date

object Logger {
    private const val TAG = "CollegeAlert"

    // Writes a log message to both Logcat and an internal file for post-mortem inspection
    fun log(context: Context?, tag: String, message: String) {
        val line = "[${Date()}] $tag: $message"
        Log.d(TAG, line)
        try {
            if (context != null) {
                val f = File(context.filesDir, "app_events.log")
                f.appendText(line + "\n")
            }
        } catch (_: Exception) {
            // ignore file write failures
        }
    }

    fun logException(context: Context?, tag: String, throwable: Throwable) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        throwable.printStackTrace(pw)
        log(context, tag, sw.toString())
    }
}

