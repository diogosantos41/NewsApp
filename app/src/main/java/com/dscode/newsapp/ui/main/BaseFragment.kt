package com.dscode.newsapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dscode.newsapp.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    protected val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOnBackPressedHandle()
    }

    private fun getViewContainer(): View = (activity as MainActivity).getViewContainer()

    private fun setupOnBackPressedHandle() {
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

    protected fun notifyWithAction(
        message: Int,
        actionText: Int,
        action: () -> Any
    ) {
        val snackBar = Snackbar.make(getViewContainer(), message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        snackBar.show()
    }

}