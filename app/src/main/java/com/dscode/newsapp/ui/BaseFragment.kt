package com.dscode.newsapp.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private fun showProgressBar() {
        with(activity) {
            if (this is MainActivity) this.showProgressBar()
        }
    }

    private fun hideProgressBar() {
        with(activity) {
            if (this is MainActivity) this.hideProgressBar()
        }
    }
}