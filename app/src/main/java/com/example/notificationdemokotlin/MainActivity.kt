package com.example.notificationdemokotlin

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.notificationdemokotlin.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var count = 0
    override fun onResume() {
        super.onResume()
        count = 0
    }
    @SuppressLint("LaunchActivityFromNotification")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("token:", it)
        }

        binding.btnHigh.setOnClickListener {
            count ++ ;
            val notification=NotificationCompat.Builder(this,App().CHANNEL_ID1)
            val intent=Intent(this,MainActivity2::class.java)
            intent.putExtra("DATA_REC",binding.edtMessage.text.toString())// passing data from the notification
            val pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE )
            notification.setContentTitle(binding.edtTitle.text.toString())
            notification.setContentText(binding.edtMessage.text.toString())
            notification.setSmallIcon(R.drawable.ic_launcher_foreground)
            notification.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)// we setting the category according to the type of notification
                .setColor(Color.MAGENTA)
                .setNumber(count)
                .setContentIntent(pendingIntent)// on click of notification what should happen
                .addAction(R.drawable.ic_launcher_foreground,"Back",pendingIntent)//max we can define 3 action on a notification
                .addAction(R.drawable.ic_launcher_foreground,"Play",null)// in short we adding button to notification
                .addAction(R.drawable.ic_launcher_foreground,"next",null)// here we what should happen on click of notification or when we have to add something in notification
                .setOnlyAlertOnce(false)// we have same  notification then it will  not alert
                .setAutoCancel(true) // on click on notification it will cancel
                .build()
            val manager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1,notification.build())

        }



        binding.btnLow.setOnClickListener {
            val notification=NotificationCompat.Builder(this,App().CHANNEL_ID2)

            // here we use broadcast reciver
            val intent=Intent(this,BroadCastReciver::class.java)
            intent.putExtra("DATA_REC",binding.edtMessage.text.toString())// passing data from the notification
            val pendingIntent=PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE )

            notification.setContentTitle(binding.edtTitle.text.toString())
            notification.setContentText(binding.edtMessage.text.toString())
            notification.setSmallIcon(R.drawable.ic_launcher_foreground)
            notification.setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)// we setting the category according to the type of notification
                .setColor(Color.MAGENTA)
                .setContentIntent(pendingIntent)// on click of notification what should happen
                .addAction(R.drawable.ic_launcher_foreground,"Back",pendingIntent)//max we can define 3 action on a notification
                .addAction(R.drawable.ic_launcher_foreground,"Play",null)// in short we adding button to notification
                .addAction(R.drawable.ic_launcher_foreground,"next",null)// here we what should happen on click of notification or when we have to add something in notification

                .setOnlyAlertOnce(true)// we have same  notification then it will  not alert
                .setAutoCancel(true) // on click on notification it will cancel
                .build()
            val manager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(2,notification.build())
        }
    }

}