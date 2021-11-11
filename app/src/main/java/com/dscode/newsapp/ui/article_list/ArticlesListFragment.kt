package com.dscode.newsapp.ui.article_list

import android.os.Bundle
import android.view.*
import android.widget.Toast
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
        getNews()
        setupRecyclerView()
    }

    private fun getNews() {
        showProgressBar()
        viewModel.callGetNews("us") // TODO get dynamic country code
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
        gridLayoutManager = StaggeredGridLayoutManager(SPAN_COUNT_TWO, StaggeredGridLayoutManager.VERTICAL)
        articleAdapter = ArticleAdapter(this@ArticlesListFragment, gridLayoutManager)
        adapter = articleAdapter
        layoutManager = gridLayoutManager
    }

    override fun onItemClicked(article: Article) {
        viewModel.selectArticle(article)
    }


    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.ListIsEmpty -> {
                binding?.articlesRv?.invisible()
                // TODO Show Empty View
            }
            is Failure.NetworkConnection -> {
                Toast.makeText(
                    context,
                    getString(R.string.failure_network_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Failure.UnexpectedError -> {
                Toast.makeText(
                    context,
                    getString(R.string.failure_unexpected_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
        activity?.menuInflater?.inflate(R.menu.menu_articles_list, menu)
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
        binding?.articlesRv?.adapter = null
        _binding = null
    }
}