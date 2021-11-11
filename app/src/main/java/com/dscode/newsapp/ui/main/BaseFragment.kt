package com.dscode.newsapp.ui.main

import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

abstract class BaseFragment : Fragment() {

    protected val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleOnBackPressed()
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (activity?.supportFragmentManager?.backStackEntryCount == 1) {
                activity?.finish()
            } else {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

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
}