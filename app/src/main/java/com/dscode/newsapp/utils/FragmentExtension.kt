package com.dscode.newsapp.utils

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dscode.newsapp.ui.main.BaseFragment

fun FragmentManager.add(containerViewId: Int, fragment: BaseFragment) {
    this.beginTransaction()
        .add(containerViewId, fragment)
        .commit()
}

fun Fragment.openUrl(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}