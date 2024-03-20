package com.example.notificationdemokotlin

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import com.example.notificationdemokotlin.databinding.ActivityMainBinding
import com.example.notificationdemokotlin.service.ForegroundService
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var count = 0
    override fun onResume() {
        super.onResume()
        count = 0
    }
    @SuppressLint("LaunchActivityFromNotification", "MissingPermission", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("token:", it)
        }
        val notification=NotificationCompat.Builder(this,App().CHANNEL_ID2)

        // here we use broadcast reciver
        val intent=Intent(this,BroadCastReciver::class.java)
        intent.putExtra("DATA_REC",binding.edtMessage.text.toString())// passing data from the notification
        val pendingIntent=PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE )

        notification.setContentTitle("Downloading")
        notification.setContentText(binding.edtMessage.text.toString())
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)// we setting the category according to the type of notification
            .setColor(Color.MAGENTA)
            .setContentIntent(pendingIntent)// on click of notification what should happen
            .addAction(R.drawable.ic_launcher_foreground,"Cancel",pendingIntent)//max we can define 3 action on a notification
            .addAction(R.drawable.ic_launcher_foreground,"Pause",null)// in short we adding button to notification
//                .addAction(R.drawable.ic_launcher_foreground,"next",null)// here we what should happen on click of notification or when we have to add something in notification
            .setOnlyAlertOnce(true)// we have same  notification then it will  not alert
            .setAutoCancel(true) // on click on notification it will cancel
            .build()



        binding.btnHigh.setOnClickListener {
            count ++ ;
            try {
                val alarmSound = Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE
                            + "://" + this.packageName + "/raw/q"
                )
                val r = RingtoneManager.getRingtone(this, alarmSound)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }

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
        binding.btnD.setOnClickListener {

            val PROGRESS_MAX = 100
            var PROGRESS_CURRENT = 0
            NotificationManagerCompat.from(this).apply {
                // Issue the initial notification with zero progress.
                notification.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
                notify(3, notification.build())

                runBlocking{do{ PROGRESS_CURRENT+=25
                   Thread.sleep(1000)
                   Log.d("aaaa",PROGRESS_CURRENT.toString())} while (PROGRESS_CURRENT!=PROGRESS_MAX)}
                // Do the job that tracks the progress here.
                // Usually, this is in a worker thread.
                // To show progress, update PROGRESS_CURRENT and update the notification with:
                notification.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);

                this.notify(3,notification.build())

                // When done, update the notification once more to remove the progress bar.
                if (PROGRESS_CURRENT==PROGRESS_MAX){
                    notification.setContentText("Download complete")
                        .setProgress(100, 100, false)
                    notify(3, notification.build())
                }

            }
        }
        binding.btnReply.setOnClickListener {
            val notification=NotificationCompat.Builder(this,App().CHANNEL_ID2)
            // Key for the string that's delivered in the action's intent.
            val KEY_TEXT_REPLY = "key_text_reply"
            var replyLabel: String = "Reply"
            var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
                setLabel(replyLabel)
                build()
            }
            // here we use broadcast reciver
            val intent=Intent(this,BroadCastReciver::class.java)
            intent.putExtra("DATA_REC",binding.edtMessage.text.toString())// passing data from the notification


            // Build a PendingIntent for the reply action to trigger.
            var replyPendingIntent: PendingIntent =
                PendingIntent.getBroadcast(applicationContext,
                    3,
                    intent,
                    PendingIntent.FLAG_MUTABLE)
            // Create the reply action and add the remote input.
            var action: NotificationCompat.Action =
                NotificationCompat.Action.Builder(
                    R.drawable.baseline_reply_24,
                    "Reply", replyPendingIntent)
                    .addRemoteInput(remoteInput)
                    .build()

            notification.setContentTitle("Vaibhav")
            notification.setContentText(binding.edtMessage.text.toString()+"Hello")
            notification.setSmallIcon(R.drawable.ic_launcher_foreground)
            notification.setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)// we setting the category according to the type of notification
                .setColor(Color.MAGENTA)
                .setContentIntent(pendingIntent)// on click of notification what should happen\
                .addAction(action)
                .addAction(R.drawable.ic_launcher_foreground,"Cancel",pendingIntent)// in short we adding button to notification

                .setOnlyAlertOnce(true)// we have same  notification then it will  not alert
                .setAutoCancel(true) // on click on notification it will cancel
                .build()

            val manager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(3,notification.build())


        }

        binding.btnCall.setOnClickListener {
            // Create a new call, setting the user as the caller.
            val intent=Intent(this,ForegroundService::class.java)
            intent.putExtra("name","Vaibhav")
            startForegroundService(intent)
        }

    }



}