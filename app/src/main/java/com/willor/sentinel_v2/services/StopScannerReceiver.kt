package com.willor.sentinel_v2.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


/*
TODO
    - Don't forget to add to manifest.xml
    - When the user clicks "Stop Scanner" on the notification, This broadcast receiver will
    be called to stop the scanner and clear the notification.
 */
class StopScannerReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("Not yet implemented")
    }
}