package com.dscode.newsapp.ui.article_list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dscode.newsapp.R
import com.dscode.newsapp.common.Failure
import com.dscode.newsapp.data.remote.model.Article
import com.dscode.newsapp.databinding.FragmentArticleListBinding
import com.dscode.newsapp.ui.article_list.ArticleAdapter.Companion.SPAN_COUNT_ONE
import com.dscode.newsapp.ui.article_list.ArticleAdapter.Companion.SPAN_COUNT_TWO
import com.dscode.newsapp.ui.main.BaseFragment
import com.dscode.newsapp.utils.invisible
import com.dscode.newsapp.utils.visible


class ArticlesListFragment : BaseFragment(), ArticleAdapter.OnItemClickListener {

    private var _binding: FragmentArticleListBinding? = null
    private val binding get() = _binding

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var gridLayoutManager: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setupObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupObservers() {
        viewModel.getNews().observe(this, {
            hideEmptyView()
            articleAdapter.submitList(it.articles)
        })

        viewModel.onFailure().observe(this, {
            showEmptyView()
            handleFailure(it)
        })

        viewModel.onCategoryQueryChange().observe(this, {
            handleCategoryChange(it)
        })
    }

    private fun setupRecyclerView() = binding?.articlesListRv?.apply {
        gridLayoutManager =
            StaggeredGridLayoutManager(SPAN_COUNT_TWO, StaggeredGridLayoutManager.VERTICAL)
        articleAdapter = ArticleAdapter(this@ArticlesListFragment, gridLayoutManager)
        adapter = articleAdapter
        layoutManager = gridLayoutManager
    }

    private fun refreshNews() {
        viewModel.callGetNews()
    }

    private fun clearQuery() {
        viewModel.clearQuery()
    }

    override fun onItemClicked(article: Article) {
        viewModel.selectArticle(article)
    }

    private fun handleCategoryChange(it: String) {
        if (it.isNotEmpty()) {
            binding?.articlesListSearchFilterLl?.visible()
            binding?.articlesListSearchFilterQueryTv?.text =
                getString(R.string.article_list_searching_category, it)
            binding?.articlesListSearchFilterClearTv?.setOnClickListener {
                binding?.articlesListSearchFilterLl?.invisible()
                clearQuery()
            }
        } else {
            binding?.articlesListSearchFilterLl?.invisible()
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.ListIsEmpty -> {
                // --
            }
            is Failure.NetworkConnection -> {
                notifyWithAction(
                    getString(R.string.failure_network_connection),
                    getString(R.string.generic_retry),
                    ::refreshNews
                )
            }
            is Failure.UnexpectedError -> {
                notifyWithAction(
                    getString(R.string.failure_unexpected_error),
                    getString(R.string.generic_retry),
                    ::refreshNews
                )
            }
        }
    }

    private fun showEmptyView() {
        binding?.articlesListEmptyView?.root?.visible()
        binding?.articlesListRv?.invisible()
    }

    private fun hideEmptyView() {
        binding?.articlesListEmptyView?.root?.invisible()
        binding?.articlesListRv?.visible()
    }

    override fun getToolbarTitle(): String {
        return if (viewModel.countryName.value.isNullOrEmpty()) {
            getString(R.string.article_list_title_without_location)
        } else {
            getString(R.string.article_list_title, viewModel.countryName.value)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
        activity?.menuInflater?.inflate(R.menu.menu_articles_list, menu)
        setupSearchView(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_change_list_layout -> {
                switchLayout()
                switchIcon(item)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupSearchView(menu: Menu) {
        val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = getString(R.string.article_list_search_query_hint)

        var mQueryTextView =
            searchView.findViewById<View>(R.id.search_src_text) as AutoCompleteTextView
        mQueryTextView.setDropDownBackgroundResource(R.color.colorPrimary)
        var newsAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_search_dropdown,
            resources.getStringArray(R.array.search_category_hint)
        )
        mQueryTextView.setAdapter(newsAdapter)
        mQueryTextView.onItemClickListener =
            AdapterView.OnItemClickListener { _: AdapterView<*>, view1: View, _: Int, _: Long ->
                searchView.setQuery(((view1 as TextView).text as String), true)
            }


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String): Boolean {
                searchView.clearFocus()
                searchItem.collapseActionView()
                viewModel.updateSearchQuery(queryText)
                return true
            }

            override fun onQueryTextChange(queryText: String?): Boolean {
                return false
            }
        })
    }

    private fun switchLayout() {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            gridLayoutManager.spanCount = SPAN_COUNT_TWO
        } else {
            gridLayoutManager.spanCount = SPAN_COUNT_ONE
        }
        articleAdapter.notifyItemRangeChanged(0, articleAdapter.itemCount)
    }

    private fun switchIcon(item: MenuItem) {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            item.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_toolbar_grid, null)
        } else {
            item.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_toolbar_compact, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.articlesListRv?.adapter = null
        _binding = null
    }
}