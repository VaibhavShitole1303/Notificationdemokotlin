package com.example.notificationdemokotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.HttpURLConnection
import java.net.URL


class FirebaseMessageReceiver: FirebaseMessagingService(){

    val CHANNEL_ID = "1001"
    private val CHANNEL_NAME = "default"

    private lateinit var mNotificationHelper: NotificationHelper
    override fun onNewToken(token: String) {
        Log.d("Notification", "Refreshed token: $token")
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("Notification", "onMessageReceivedCalled")
        mNotificationHelper = NotificationHelper()
        if (remoteMessage.notification != null) {
            if((remoteMessage.notification!!.imageUrl !=null) && (remoteMessage.notification!!.imageUrl.toString() !="")){
               try {
                   var bitmapFromUrl: Bitmap? = getBitmapFromUrl(remoteMessage.notification!!.imageUrl?.toString())
                   if (bitmapFromUrl != null) {
                       mNotificationHelper.sendPictureNotification(this, remoteMessage.notification?.title, remoteMessage.notification?.body,bitmapFromUrl)
                   }else{
                       mNotificationHelper.sendNotification(this, remoteMessage.notification?.title, remoteMessage.notification?.body)
                   }
               }catch (e:Exception){
                   mNotificationHelper.sendNotification(this, remoteMessage.notification?.title, remoteMessage.notification?.body)
               }

            }else{
                mNotificationHelper.sendNotification(this, remoteMessage.notification?.title, remoteMessage.notification?.body)
            }
        }
    }

    private fun getBitmapFromUrl(imageUrl: String?): Bitmap? {
        val bitmap: Bitmap
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            bitmap = BitmapFactory.decodeStream(input)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}