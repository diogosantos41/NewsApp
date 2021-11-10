package com.dscode.newsapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.dscode.newsapp.R
import com.dscode.newsapp.common.Failure
import com.dscode.newsapp.data.remote.model.Article
import com.dscode.newsapp.databinding.FragmentArticleListBinding
import com.dscode.newsapp.ui.ArticleAdapter.Companion.SPAN_COUNT_ONE
import com.dscode.newsapp.ui.ArticleAdapter.Companion.SPAN_COUNT_THREE
import com.dscode.newsapp.utils.invisible


class ArticlesListFragment : BaseFragment(), ArticleAdapter.OnItemClickListener {

    private var _binding: FragmentArticleListBinding? = null
    private val binding get() = _binding

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

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
        getNews()
        setupRecyclerView()
    }

    private fun getNews() {
        showProgressBar()
        viewModel.callGetNews()
    }

    private fun setupObservers() {
        viewModel.getNews().observe(this, {
            articleAdapter.submitList(it.articles)
            hideProgressBar()
        })

        viewModel.onFailure().observe(this, {
            handleFailure(it)
            hideProgressBar()
        })
    }

    private fun setupRecyclerView() = binding?.articlesRv?.apply {
        gridLayoutManager = GridLayoutManager(context, SPAN_COUNT_ONE)
        articleAdapter = ArticleAdapter(this@ArticlesListFragment, gridLayoutManager)
        adapter = articleAdapter
        layoutManager = gridLayoutManager
    }

    override fun onItemClicked(article: Article) {
        TODO("Not yet implemented")
    }


    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.ListIsEmpty -> {
                binding?.articlesRv?.invisible()
                // TODO Show Empty View
            }
            is Failure.NetworkConnection -> {
                Toast.makeText(context, "Cannot connect to network, check internet", Toast.LENGTH_SHORT).show()
            }
            is Failure.UnexpectedError -> {
                Toast.makeText(context, "Unexpected error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!isLoading()) {
            when (item.itemId) {
                R.id.menu_item_change_list_layout -> {
                    switchLayout()
                    switchIcon(item)
                    true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchLayout() {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            gridLayoutManager.spanCount = SPAN_COUNT_THREE
        } else {
            gridLayoutManager.spanCount = SPAN_COUNT_ONE
        }
        articleAdapter.notifyItemRangeChanged(0, articleAdapter.itemCount)
    }

    private fun switchIcon(item: MenuItem) {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            item.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_toolbar_grid, null)
        } else {
            item.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_toolbar_list, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.articlesRv?.adapter = null
        _binding = null
    }
}