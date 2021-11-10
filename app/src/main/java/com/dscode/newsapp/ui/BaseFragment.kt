package com.dscode.newsapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

abstract class BaseFragment : Fragment() {

    protected val viewModel by activityViewModels<MainViewModel>()

    protected fun showProgressBar() {
        with(activity) {
            if (this is MainActivity) this.showProgressBar()
        }
    }

    protected fun hideProgressBar() {
        with(activity) {
            if (this is MainActivity) this.hideProgressBar()
        }
    }

    protected fun close() {
        activity?.let { it.supportFragmentManager.popBackStack() }
    }
}