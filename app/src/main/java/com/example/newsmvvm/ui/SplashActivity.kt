package com.example.newsmvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.newsmvvm.R
import com.example.newsmvvm.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
                               startHomeActivity()
        },2000)
    }

    private fun startHomeActivity() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }
}