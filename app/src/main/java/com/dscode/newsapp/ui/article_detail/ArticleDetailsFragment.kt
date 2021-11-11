package com.dscode.newsapp.ui.article_detail

import android.os.Bundle
import android.view.*
import androidx.core.text.HtmlCompat
import com.dscode.newsapp.R
import com.dscode.newsapp.databinding.FragmentArticleDetailBinding
import com.dscode.newsapp.ui.main.BaseFragment
import com.dscode.newsapp.utils.convertServerDateToDisplayDate
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
        fillWithArticleData()
    }

    private fun fillWithArticleData() {
        viewModel.getSelectedArticle()?.let {
            with(binding) {
                this?.articleDetailTitleTv?.text = it.title
                this?.articleDetailPublishedDateTv?.text =
                    getString(R.string.article_date_published_on)
                        .plus(" ")
                        .plus(convertServerDateToDisplayDate(it.publishedAt))
                this?.articleDetailSourceTv?.text =
                    getString(R.string.article_source_by)
                        .plus(" ")
                        .plus(it.source.name)
                this?.articleDetailThumbnailIv?.loadFromUrl(it.urlToImage)
                if (!it.content.isNullOrEmpty()) {
                    this?.articleDetailContentTv?.text =
                        HtmlCompat.fromHtml(it.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
                }
            }
        }
    }

    override fun getToolbarTitle(): String {
        return getString(R.string.article_details_title)
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