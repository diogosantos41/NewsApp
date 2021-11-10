package com.dscode.newsapp.ui.article_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dscode.newsapp.databinding.FragmentArticleDetailBinding
import com.dscode.newsapp.ui.main.BaseFragment

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
                it.source.id
            }
        }
        // TODO ui stuff
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}