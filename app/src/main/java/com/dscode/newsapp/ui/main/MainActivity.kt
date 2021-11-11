package com.dscode.newsapp.ui.main

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dscode.newsapp.common.Constants.DEFAULT_COUNTRY_CODE
import com.dscode.newsapp.data.repository.RepositoryImpl
import com.dscode.newsapp.databinding.ActivityMainBinding
import com.dscode.newsapp.ui.article_detail.ArticleDetailsFragment
import com.dscode.newsapp.ui.article_list.ArticlesListFragment
import com.dscode.newsapp.utils.*


class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupObservers()
        addFragment(ArticlesListFragment(), savedInstanceState)
        setupLocationGetter()
    }

    private fun setupObservers() {
        viewModel.onSelectedArticleChange().observe(this, {
            addFragment(ArticleDetailsFragment())
        })
    }

    private fun setupViewModel() {
        val repository = RepositoryImpl()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private fun setupLocationGetter() {
        if (canSetupLocationService(this)) {
            setupLocationService(this, this)
        } else {
            viewModel.updateCountryCode(DEFAULT_COUNTRY_CODE)
        }
    }

    fun getViewContainer(): View {
        return binding.fragmentContainer
    }

    fun showProgressBar() {
        binding.loadingViewLl.visible()
    }

    fun hideProgressBar() {
        binding.loadingViewLl.invisible()
    }

    fun isLoading(): Boolean {
        return binding.loadingViewLl.isVisible()
    }

    private fun addFragment(fragment: BaseFragment) =
        addFragment(fragment, null)


    private fun addFragment(fragment: BaseFragment, savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.add(getViewContainer().id, fragment)

    override fun onLocationChanged(location: Location) {
        viewModel.updateCountryCode(getCountryCodeFromLocation(this, location))
    }
}