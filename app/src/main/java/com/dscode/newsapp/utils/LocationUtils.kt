package com.dscode.newsapp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dscode.newsapp.common.Constants.DEFAULT_COUNTRY_CODE
import com.dscode.newsapp.common.Constants.DEFAULT_COUNTRY_NAME
import java.io.IOException
import java.util.*


const val COUNTRY_NAME = "COUNTRY NAME"
const val COUNTRY_CODE = "COUNTRY_CODE"

fun hasLocationPermissions(activity: AppCompatActivity): Boolean {
    return ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
fun setupLocationService(
    activity: AppCompatActivity,
    locationListener: LocationListener
) {
    var locationManager =
        activity.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
    locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        0.toLong(),
        0.toFloat(),
        locationListener
    )
}

fun isGPSEnabled(activity: AppCompatActivity): Boolean {
    var locationManager =
        activity.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun canSetupLocationService(activity: AppCompatActivity): Boolean {
    return hasLocationPermissions(activity) && isGPSEnabled(activity)
}

fun getCountryInfoFromLocation(
    activity: AppCompatActivity,
    location: Location,
    infoTag: String
): String {
    return try {
        val addresses: List<Address> = Geocoder(activity, Locale.getDefault()).getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
        when (infoTag) {
            COUNTRY_CODE -> addresses[0].countryCode
            COUNTRY_NAME -> addresses[0].countryName
            else -> "-"
        }
    } catch (e: IOException) {
        when (infoTag) {
            COUNTRY_CODE -> DEFAULT_COUNTRY_CODE
            COUNTRY_NAME -> DEFAULT_COUNTRY_NAME
            else -> "-"
        }
    }
}