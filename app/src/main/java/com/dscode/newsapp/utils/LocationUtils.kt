package com.dscode.newsapp.utils

import android.annotation.SuppressLint
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import com.dscode.newsapp.common.Constants.DEFAULT_COUNTRY_CODE
import java.io.IOException
import java.util.*


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

fun getCountryCodeFromLocation(activity: AppCompatActivity, location: Location): String {
    return try {
        val addresses: List<Address> = Geocoder(activity, Locale.getDefault()).getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
        addresses[0].countryCode
    } catch (e: IOException) {
        DEFAULT_COUNTRY_CODE
    }
}