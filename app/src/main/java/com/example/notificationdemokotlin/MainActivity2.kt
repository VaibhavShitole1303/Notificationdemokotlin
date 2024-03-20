package com.example.notificationdemokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.notificationdemokotlin.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvMessage.text = intent.getStringExtra("DATA_REC")
        intent.getStringExtra("DATA_REC")?.let { Log.d("DATA_REC,", it) }
    }
}