package ru.androidschool.intensiv.data

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ExtensionMethods {

    fun ImageView.loadImage(string: String) {

        Picasso.get()
            .load(string)
            .into(this)

    }

}