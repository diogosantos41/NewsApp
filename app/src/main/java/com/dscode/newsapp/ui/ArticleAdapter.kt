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
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val spanCountOne = 1
    private val spanCountThree = 3
    private val viewTypeGrid = 1
    private val viewTypeCompact = 2

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
        if (viewType == viewTypeCompact) {
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
        return if (spanCount == spanCountOne) {
            viewTypeCompact
        } else {
            viewTypeGrid
        }
    }

    fun submitList(list: ArrayList<Article>) {
        differ.submitList(list)
        //notifyDataSetChanged()
    }

    interface OnItemClickListener {
        // TODO viewModel receives click
        fun onItemClicked(article: Article)
    }
}