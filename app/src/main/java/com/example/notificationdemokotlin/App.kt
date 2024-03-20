package com.example.notificationdemokotlin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App: Application() {
    // creating 2 channel because one is high priority and other is low
      final val CHANNEL_ID1="CHANNEL_ID1"
      final val CHANNEL_ID2="CHANNEL_ID1"
    override fun onCreate() {
        super.onCreate()
        //we checking the current device api level is greater or equal to 26
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            // creating high imp channel
            val channel1=NotificationChannel(CHANNEL_ID1,
                "Channel 1",NotificationManager.IMPORTANCE_HIGH)
            channel1.description="this is high importance channel"
            // creating default imp channel
            val channel2=NotificationChannel(CHANNEL_ID1,
                "Channel 2",NotificationManager.IMPORTANCE_DEFAULT)
            channel2.description="this is high IMPORTANCE_DEFAULT channel"

            // creating notification channel
            val manager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
        }
    }
}