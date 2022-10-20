package com.willor.lib_data.utils

import android.util.Log
import com.google.gson.Gson


val gson = Gson()


fun logException(tag: String, exception: Exception, extraInfo: String? = null){

    val msg = if (extraInfo != null){
        "$extraInfo -- Exception: ${exception}\nStacktrace: ${exception.stackTraceToString()}"
    }else{
        "Exception: ${exception}\nStacktrace: ${exception.stackTraceToString()}"
    }

    Log.w(tag, msg)
}