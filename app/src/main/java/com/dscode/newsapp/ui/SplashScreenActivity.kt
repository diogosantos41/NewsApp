package com.dscode.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dscode.newsapp.common.Constants.SPLASH_SCREEN_DURATION
import com.dscode.newsapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DURATION)
    }
}