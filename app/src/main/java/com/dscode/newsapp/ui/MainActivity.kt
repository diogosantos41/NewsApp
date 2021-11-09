package com.dscode.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dscode.newsapp.R
import com.dscode.newsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}