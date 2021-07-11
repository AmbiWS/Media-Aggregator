package ru.androidschool.intensiv.domain.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageViewExtensions {

    fun ImageView.loadImage(string: String) {

        Picasso.get()
            .load(string)
            .into(this)
    }
}
