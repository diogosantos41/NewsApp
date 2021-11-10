package com.dscode.newsapp.utils

import androidx.fragment.app.FragmentManager
import com.dscode.newsapp.ui.main.BaseFragment

fun FragmentManager.add(containerViewId: Int, fragment: BaseFragment) {
    this.beginTransaction()
        .add(containerViewId, fragment)
        .commit()
}