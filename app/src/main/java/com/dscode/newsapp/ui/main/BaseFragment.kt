package com.dscode.newsapp.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

abstract class BaseFragment : Fragment() {

    protected val viewModel by activityViewModels<MainViewModel>()

    protected fun showProgressBar() {
        if (activity is MainActivity) (activity as MainActivity).showProgressBar()
    }

    protected fun hideProgressBar() {
        if (activity is MainActivity) (activity as MainActivity).hideProgressBar()

    }

    protected fun isLoading(): Boolean {
        if (activity is MainActivity) return (activity as MainActivity).isLoading()
        return false
    }

    protected fun close() {
        activity?.let { it.supportFragmentManager.popBackStack() }
    }
}