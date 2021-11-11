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
        var supportFragmentManager = activity?.supportFragmentManager
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager?.backStackEntryCount == 1) {
                activity?.finish()
            } else {
                supportFragmentManager?.popBackStack()
                try {
                    supportFragmentManager?.fragments?.get(supportFragmentManager?.fragments.size - 2)
                        ?.onResume()
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getToolbarTitle())
    }

    open fun getToolbarTitle(): String {
        return ""
    }

    private fun setToolbarTitle(string: String) {
        if (activity is MainActivity) (activity as MainActivity).title = string
    }

    protected fun isLoading(): Boolean {
        if (activity is MainActivity) return (activity as MainActivity).isLoading()
        return false
    }

    protected fun notify(message: String) =
        Snackbar.make(getViewContainer(), message, Snackbar.LENGTH_SHORT).show()

    protected fun notifyWithAction(
        message: String,
        actionText: String,
        action: () -> Any
    ) {
        val snackBar = Snackbar.make(getViewContainer(), message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        snackBar.show()
    }

}