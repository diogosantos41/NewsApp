package com.dscode.newsapp.ui.article_detail

import android.os.Bundle
import android.view.*
import com.dscode.newsapp.R
import com.dscode.newsapp.databinding.FragmentArticleDetailBinding
import com.dscode.newsapp.ui.main.BaseFragment
import com.dscode.newsapp.utils.loadFromUrl
import com.dscode.newsapp.utils.openUrl


class ArticleDetailsFragment : BaseFragment() {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSelectedArticle()?.let {
            with(binding) {
                this?.articleDetailThumbnailIv?.loadFromUrl(it.urlToImage)
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
        activity?.menuInflater?.inflate(R.menu.menu_article_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!isLoading()) {
            when (item.itemId) {
                R.id.menu_item_browser -> {
                    viewModel.getSelectedArticle()?.url?.let { openUrl(it) }
                    true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}