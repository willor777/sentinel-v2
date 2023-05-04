package com.willor.sentinel_v2.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import com.willor.lib_data.domain.Repo
import com.willor.sentinel_v2.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


// TODO Scanner should only thow a trigger once every 4 hours or so...Don't annoy user!!!

/**
 * Scanner that analyzes stock charts periodically according to specified interval.
 *
 * Triggers found are saved in the database
 */
@AndroidEntryPoint
class StockScannerService : Service() {

    @Inject
    lateinit var repo: Repo

    private val tag: String = StockScannerService::class.java.simpleName
    private val binder = SentinelScannerBinder()
    private val notificationId: Int = 8
    private val notificationChannelId: String = "STOCK_SCANNER"
    private val notificationChannelName: String = "Stock Scanner"

    // This scope is canceled in onDestroy()
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)


    override fun onBind(intent: Intent?): IBinder {
        Log.d(tag, "onBind() called")
        return binder
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(tag, "onStartCommand() called")

        // Start foreground providing the notificationId + a Notification
        createNotificationChannel()
        startForeground(notificationId, createNotification("Scanner Running"))

        return START_STICKY
    }


    override fun stopService(name: Intent?): Boolean {
        Log.d(tag, "stopService() called")
        return super.stopService(name)
    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        Log.d(tag, "onDestroy() called")
    }


    // TODO Add checks for version codes lower than < Oreo
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(
                notificationChannelId,
                notificationChannelName, NotificationManager.IMPORTANCE_HIGH
            )
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val service =
                this.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(chan)
        }
    }


    // TODO Set small icon
    private fun createNotification(notificationText: String): Notification {

        // Pending Intent calls 'StopScannerReceiver', Which then stops the service
        val dismissIntent = PendingIntent.getBroadcast(
            this,
            8,
            Intent(this, StopScannerReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, notificationChannelId)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_HIGH)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)
            .addAction(R.drawable.ic_scanner_icon_24, "Dismiss", dismissIntent)
            .setContentText(notificationText)
            .build()
    }


    private fun updateNotification(newNotification: Notification) {
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(notificationId, newNotification)
    }


    /*
     * TODO - Scanner Process Rough Outline
     *      - Get Charts for Sentinel Watchlist Tickers
     *      - Apply Analysis to Each Chart
     *      - If trigger is found, save it to the data base
     *      - Scanner should update it's current "status" as a Database entry to be displayed
     *
     * Notes * * * * *
     * - Remember to only fetch charts after full candle has been formed (This improves efficiency)
     */
    private fun startScanner() {
        // Default dispatcher is used for cpu itensive tasks
        coroutineScope.launch(Dispatchers.Default) {



        }
    }





    inner
    class SentinelScannerBinder : Binder() {

        // This function can be called to retrieve an instance of the service
        fun getService(): StockScannerService {
            return this@StockScannerService
        }

    }

}

















