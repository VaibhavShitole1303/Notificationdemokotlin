package com.example.notificationdemokotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadCastReciver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
       Toast.makeText(context,intent?.getStringExtra("DATA_REC"),Toast.LENGTH_LONG).show()
    }
}