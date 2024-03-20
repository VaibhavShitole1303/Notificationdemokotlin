package com.example.notificationdemokotlin.fcm

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationdemokotlin.MainActivity
import com.example.notificationdemokotlin.R


class NotificationHelper {
    private lateinit var mMyFirebaseService: FirebaseMessageReceiver

    var count = 0
    lateinit var  intent: Intent
    @SuppressLint("MissingPermission")
    fun sendPictureNotification(
        context: Context,
        title: String?,
        body: String?,
        bitmap: Bitmap
    ) {

        if (title != null) {
          // redirectTo(context,redirectTo,id,plan_id)
             intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            mMyFirebaseService = FirebaseMessageReceiver()
            val mBuilder = NotificationCompat.Builder(context, mMyFirebaseService.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound( Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE
                            + "://" + context.packageName + "/raw/q"))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setLargeIcon(bitmap)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setColor(context.getColor(R.color.white))
                .setAutoCancel(true)
            val notificationManager = NotificationManagerCompat.from(context)
            val notificationId = (System.currentTimeMillis() / 4).toInt()
            notificationManager.notify(notificationId, mBuilder.build())

            try {
                val alarmSound = Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE
                            + "://" + context.packageName + "/raw/q"
                )
                val r = RingtoneManager.getRingtone(context, alarmSound)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun sendNotification(context: Context, title: String?, body: String?) {
        if (title != null) {
            count++
            intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            mMyFirebaseService = FirebaseMessageReceiver()
            val mBuilder = NotificationCompat.Builder(context, mMyFirebaseService.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(defaultUri)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setColor(context.getColor(R.color.white))
                .setAutoCancel(true)

            val notificationManager = NotificationManagerCompat.from(context)
            val notificationId = (System.currentTimeMillis() / 4).toInt()
            notificationManager.notify(notificationId, mBuilder.build())

            try {
                val alarmSound = Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE
                            + "://" + context.packageName + "/raw/q"
                )
                val r = RingtoneManager.getRingtone(context, alarmSound)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}