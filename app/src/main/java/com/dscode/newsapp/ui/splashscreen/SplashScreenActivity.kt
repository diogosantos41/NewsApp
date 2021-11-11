package com.dscode.newsapp.ui.splashscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.dscode.newsapp.R
import com.dscode.newsapp.common.Constants.SPLASH_SCREEN_DURATION
import com.dscode.newsapp.databinding.ActivitySplashScreenBinding
import com.dscode.newsapp.ui.main.MainActivity
import com.dscode.newsapp.utils.hasLocationPermissions


class SplashScreenActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissions()
    }

    private fun checkPermissions() {
        if (hasLocationPermissions(this)) {
            startMainActivity()
        } else {
            requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startMainActivity()
                } else {
                    if (shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        && shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    ) {
                        showPermissionExplanationDialog()
                    } else {
                        startMainActivity()
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DURATION)
    }

    private fun showPermissionExplanationDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.generic_information))
        builder.setMessage(getString(R.string.permissions_denied_message))
        builder.setIcon(R.drawable.ic_info)
        builder.setPositiveButton(R.string.generic_ok) { _, _ ->
            startMainActivity()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}