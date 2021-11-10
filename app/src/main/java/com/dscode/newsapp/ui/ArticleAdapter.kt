package com.dscode.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dscode.newsapp.data.remote.model.Article
import com.dscode.newsapp.databinding.ItemArticleCompactBinding
import com.dscode.newsapp.databinding.ItemArticleGridBinding


class ArticleAdapter(
    private val itemClickListener: OnItemClickListener,
    private val layoutManager: GridLayoutManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SPAN_COUNT_ONE = 1
        const val SPAN_COUNT_THREE = 3
        const val VIEW_TYPE_GRID = 1
        const val VIEW_TYPE_COMPACT = 2
    }

    inner class ArticleGridLayoutViewHolder(val itemBinding: ItemArticleGridBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    inner class ArticleCompactLayoutViewHolder(val itemBinding: ItemArticleCompactBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_COMPACT) {
            val itemBinding = ItemArticleCompactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ArticleCompactLayoutViewHolder(itemBinding)
        } else {
            val itemBinding =
                ItemArticleGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return ArticleGridLayoutViewHolder(itemBinding)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(differ.currentList[position]) {
            if (holder is ArticleCompactLayoutViewHolder) {
                with(holder.itemBinding) {

                }

            } else if (holder is ArticleGridLayoutViewHolder) {
                with(holder.itemBinding) {

                }
            }
        }


        // root.setOnClickListener {
        // if (holder.adapterPosition != -1) {
        //   itemClickListener.onItemClicked(this)
    }

    override fun getItemViewType(position: Int): Int {
        var spanCount = layoutManager.spanCount
        return if (spanCount == SPAN_COUNT_ONE) {
            VIEW_TYPE_COMPACT
        } else {
            VIEW_TYPE_GRID
        }
    }

    fun submitList(list: List<Article>) {
        differ.submitList(list)
        //notifyDataSetChanged()
    }

    interface OnItemClickListener {
        // TODO viewModel receives click
        fun onItemClicked(article: Article)
    }
}