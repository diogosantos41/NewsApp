package com.dscode.newsapp.utils

import android.view.View
import android.widget.ImageView
import com.dscode.newsapp.R
import com.squareup.picasso.Picasso


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun ImageView.loadFromUrl(url: String?) =
    if (url.isNullOrEmpty()) {
        setImageDrawable(resources.getDrawable(R.drawable.image_palceholder, null))
    } else {
        Picasso.get().load(url)
            .centerCrop()
            .placeholder(R.drawable.image_palceholder)
            .fit()
            .into(this);
    }
