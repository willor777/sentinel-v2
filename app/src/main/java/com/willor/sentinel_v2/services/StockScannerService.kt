package com.willor.sentinel_v2.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/*
TODO
    - Don't forget to add this and the broadcast receiver to the manifest.xml
    - Display a notification that the scanner is running
    - Make sure the notification can be updated when there is a trigger found
    -
 */

class StockScannerService: Service() {

    private val binder = SentinelScannerBinder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // display notification

        // start foreground

        return super.onStartCommand(intent, flags, startId)
    }

    inner class SentinelScannerBinder: Binder() {

        // This function can be called by binding classes to retrieve an instance of the service
        fun getService(): StockScannerService{
            return this@StockScannerService
        }

    }
}