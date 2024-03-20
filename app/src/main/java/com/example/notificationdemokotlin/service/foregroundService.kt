package com.example.notificationdemokotlin.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import com.example.notificationdemokotlin.App
import com.example.notificationdemokotlin.BroadCastReciver
import com.example.notificationdemokotlin.R

class ForegroundService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    @SuppressLint("LaunchActivityFromNotification")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       Log.d("onStartCommand","onStartCommand")
        val incomingCaller = Person.Builder()
            .setName("Vaibhav")
            .setImportant(true)

            .build()
        // Create a call style notification for an incoming call.
        val notification= NotificationCompat.Builder(this, App().CHANNEL_ID2)

        // here we use broadcast reciver
        val intent=Intent(this, BroadCastReciver::class.java)
        val pendingIntent= PendingIntent.getBroadcast(this,0,intent, PendingIntent.FLAG_IMMUTABLE )

        notification.setContentTitle("Incomming Call")
        notification.setContentText("Vaibhav")
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)// we setting the category according to the type of notification
            .setColor(Color.MAGENTA)
            .setContentIntent(pendingIntent)// on click of notification what should happen

            .setOnlyAlertOnce(true)// we have same  notification then it will  not alert
            .setAutoCancel(true)
            .addPerson(incomingCaller)
//            .setStyle(
//                NotificationCompat.CallStyle.forIncomingCall(incomingCaller, pendingIntent, pendingIntent))

//            .setStyle(
//                NotificationCompat.CallStyle.forScreeningCall(incomingCaller, pendingIntent, pendingIntent))

//            .setStyle(
//                NotificationCompat.CallStyle.forOngoingCall(incomingCaller, pendingIntent))
            .build()
        val manager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(3,notification.build())

        return START_STICKY
    }

    override fun stopService(name: Intent?): Boolean {
        Log.d("Stopping","Stopping Service")

        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}